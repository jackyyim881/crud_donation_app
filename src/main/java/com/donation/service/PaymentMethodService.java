package com.donation.service;

import com.donation.models.data.PaymentMethod;

import java.util.List;

public interface PaymentMethodService {
    PaymentMethod createPaymentMethod(PaymentMethod paymentMethod);

    PaymentMethod updatePaymentMethod(Long id, PaymentMethod paymentMethod);

    void deletePaymentMethod(Long id);

    List<PaymentMethod> getAllPaymentMethods();

    PaymentMethod getPaymentMethodById(Long id);
}
