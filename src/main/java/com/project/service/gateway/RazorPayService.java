package com.project.service.gateway;

import com.project.domain.PaymentType;
import com.project.exception.PaymentException;
import com.project.modal.Payment;
import com.project.modal.SubscriptionPlan;
import com.project.modal.User;
import com.project.payload.response.PaymentLinkResponse;
import com.project.service.SubscriptionPlanService;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RazorPayService {

    private final SubscriptionPlanService subscriptionPlanService;

    @Value("${razorpay.key.id:}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret:}")
    private String razorpayKeySecret;

    @Value("${razorpay.callback.base-url:http://localhost:5173}")
    private String callbackBaseUrl;


    /**
     * Create a Razorpay payment link for subscription
     *
     * @param user The user making the payment
     * @param payment The payment entity to track this transaction
     * @return PaymentLinkResponse containing the payment URL and link ID
     * @throws PaymentException if payment link creation fails
     */
    public PaymentLinkResponse createPaymentLink(User user, Payment payment){
        try{
            RazorpayClient razorpayClient=new RazorpayClient(razorpayKeyId,razorpayKeySecret);

            // Convert amount to paisa (1 INR = 100 paisa)
            Long amountInPaisa = payment
                    .getAmount()*(new java.math.BigDecimal("100")).intValue();

            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amountInPaisa);
            paymentLinkRequest.put("currency", payment.getCurrency());
            paymentLinkRequest.put("description", payment.getDescription());

            // Customer details
            JSONObject customer = new JSONObject();
            customer.put("name", user.getFullName());
            customer.put("email", user.getEmail());
            if (user.getPhone() != null) {
                customer.put("contact", user.getPhone());
            }
            paymentLinkRequest.put("customer", customer);

            // Notification settings
            JSONObject notify = new JSONObject();
            notify.put("email", true);
            notify.put("sms", user.getPhone() != null);
            paymentLinkRequest.put("notify", notify);

            // Enable reminders
            paymentLinkRequest.put("reminder_enable", true);

            // Callback configuration
            String successUrl = callbackBaseUrl + "/payment-success/" + payment.getId();
            String cancelUrl = callbackBaseUrl + "/payment-cancelled/" + payment.getId();

            paymentLinkRequest.put("callback_url", successUrl);
            paymentLinkRequest.put("callback_method", "get");

            // Additional metadata for tracking
            JSONObject notes = new JSONObject();
            notes.put("user_id", user.getId());
            notes.put("payment_id", payment.getId());
            if(payment.getPaymentType()== PaymentType.MEMBERSHIP){
                notes.put("subscription_id", payment.getSubscription().getId());
                notes.put("plan", payment.getSubscription().getPlan().getPlanCode());
                notes.put("type",PaymentType.MEMBERSHIP);
            }else if(payment.getPaymentType()==PaymentType.FINE){
//                notes.put("fine_id", payment.getFine().getId());
                notes.put("type",PaymentType.FINE);
            }
            paymentLinkRequest.put("notes",notes);

            // Create payment link
            PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);

            String paymentUrl = paymentLink.get("short_url");
            String paymentLinkId = paymentLink.get("id");

            PaymentLinkResponse response = new PaymentLinkResponse();
            response.setPayment_link_url(paymentUrl);
            response.setPayment_link_id(paymentLinkId);

            return response;

        }catch(RazorpayException e){
            throw new RuntimeException(e);
        }
    }


    /**
     * Fetch payment details from Razorpay
     *
     * @param paymentId Razorpay payment ID
     * @return Payment details as JSON
     * @throws PaymentException if fetch fails
     */
    public JSONObject fetchPaymentDetails(String paymentId) throws PaymentException {

        try {
            RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
            com.razorpay.Payment payment = razorpay.payments.fetch(paymentId);

            return payment.toJson();

        } catch (RazorpayException e) {
            throw new PaymentException("Failed to fetch payment details: " + e.getMessage(), e);
        }
    }

    public boolean isValidPayment(String paymentId) {
        try {

            JSONObject paymentDetails =fetchPaymentDetails(paymentId);

            String status = paymentDetails.optString("status");
            long amount = paymentDetails.optLong("amount");
            long amountInRupees = amount / 100;

            JSONObject notes = paymentDetails.getJSONObject("notes");

            String paymentType=notes.optString("type");



            // 1️⃣ Check status
            if (!"captured".equalsIgnoreCase(status)) {
                //log.warn("Payment not captured. Current status: {}", status);
                return false;
            }

            // 2️⃣ Check expected amount
            if(paymentType.equals(PaymentType.MEMBERSHIP.toString())){
                String planCode = notes.optString("plan");
                SubscriptionPlan subscriptionPlan = subscriptionPlanService
                        .getPlanByCode(planCode);
                return amountInRupees == subscriptionPlan.getPrice();
            }else if(paymentType.equals(PaymentType.FINE.toString())){
                Long fineId = notes.getLong("fine_id");
//                Fine fine =fineRepository.findById(fineId).orElseThrow(
//                        () -> new FineException("Fine not found with given id....")
//                );
//                return  fine.getAmount()==amountInRupees;
            }
            return false;
        } catch (Exception e) {
            //log.error("❌ Error verifying Razorpay payment: {}", e.getMessage(), e);
            return false;
        }
    }

}
