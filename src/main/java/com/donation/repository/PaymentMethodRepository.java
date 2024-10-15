package com.donation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.donation.models.data.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    // Custom queries can be added if needed

    PaymentMethod getPaymentMethodById(Long id);
}
