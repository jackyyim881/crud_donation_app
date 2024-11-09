package com.donation;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest(classes = SpringCrudDonationApplication.class)
class SpringCrudDonationApplicationTests {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        // This test will pass if the application context loads successfully
    }

    @Test
    void passwordEncoderBeanExists() {
        // Check if the BCryptPasswordEncoder bean is loaded correctly
        assertNotNull(passwordEncoder, "BCryptPasswordEncoder bean should not be null");
    }
}
