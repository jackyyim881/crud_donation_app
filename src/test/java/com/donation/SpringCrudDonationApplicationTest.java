package com.donation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SpringCrudDonationApplicationTest {

    @Test
    void testMain() {
        assertDoesNotThrow(() -> SpringCrudDonationApplication.main(new String[] {}));
    }
}
