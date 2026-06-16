package com.project.service.impl;

import com.project.domain.UserRole;
import com.project.modal.User;
import com.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializationComponent implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeAdminUser();
    }

    private void initializeAdminUser(){
        String adminEmail="fireflyayu@gmail.com";
        String adminPassword="Ayushi@1234";

        if(userRepository.findByEmail(adminEmail)==null){
            User user=User.builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .fullName("Ayushi garg")
                    .role(UserRole.ROLE_ADMIN)
                    .build();
            User admin=userRepository.save(user);

        }
    }
}
