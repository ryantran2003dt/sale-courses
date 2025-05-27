package com.salecoursecms.config.custom;


import com.salecoursecms.entity.first.UserEntity;
import com.salecoursecms.repository.first.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Optional<UserEntity> optional = userRepository.findByUsernameWithoutStatus("superadmin");
        if (optional.isEmpty()) {
            UserEntity admin = new UserEntity();
            admin.setUsername("superadmin");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRoldId(1L);//superadmin
            admin.setStatus(1);
            admin.setFullName("Super Admin");
            userRepository.save(admin);
        } else {
            UserEntity admin = optional.get();
            admin.setRoldId(1L);
            admin.setStatus(1);
            userRepository.save(admin);
        }
    }


}
