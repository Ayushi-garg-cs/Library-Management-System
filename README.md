
1. 🔐 Bank-Grade Security & Authentication
It’s not just "login." We use the modern security standard found in Netflix/Uber.

Google OAuth2: Users can sign in with one click using their Google Account.

JWT (JSON Web Tokens): Stateless, secure session management.

RBAC (Role-Based Access Control):

Admin Panel: Full control over books, users, and finances.

User Panel: Limited access to borrowing history, profile, and payments.

Route Protection: React Private Routes ensure users cannot hack URLs to access Admin pages.

2. 📚 Intelligent Book & Inventory Management
Managing a library is harder than it looks. We handle the edge cases.

Bulk Book Upload: Admins can upload 100s of books at once (Batch Processing).

Copy Management: The system tracks Total Copies vs Available Copies automatically.

Soft Delete: Books are never "lost." If deleted, they are marked inactive in the DB (Audit safety).

ISBN Validation: Prevents duplicate book entries.

3. 💳 Payment Gateway & Financial Module
Real integration with Razorpay.

Fine Payments: If a user is late, the system calculates the fine (e.g., ₹5/day). The user cannot borrow new books until the fine is paid via Razorpay.

Membership Subscriptions: Users can buy "Gold/Silver" plans. The system handles Plan Expiry and Auto-renewal logic.

Transaction History: Full audit trail of every rupee paid (Success/Failure/Retry logic included).

4. ⏳ Smart Reservation & Queue System
What happens if a book is out of stock?

Waiting Queue: Users can "Reserve" a book. They get added to a dynamic waiting list (Position #1, #2, etc.).

Auto-Notification: When the book is returned, the next person in the queue gets priority.

Concurrency Handling: Prevents two users from reserving the last copy at the exact same second.

5. 🔄 Lending Lifecycle Engine
The core logic of the library.

Checkout Validation: Checks if the user has an active plan? Do they have overdue fines? Have they reached their borrow limit?

Auto-Overdue Marking: A background scheduler checks for late books and marks them "Overdue" automatically.

Renewals: Users can renew a book only if no one else is waiting for it.

6. 📊 Analytics Dashboard
Data visualization using Recharts/Chart.js.

Revenue Charts: See how much money the library made this month.

User Growth: Track new signups.

Book Popularity: See which genres are trending.
