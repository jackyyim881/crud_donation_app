package com.donation.controller.v1.api;

import com.donation.models.data.PaymentMethod;
import com.donation.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-methods")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    // 1. Create a new payment method
    @PostMapping
    public ResponseEntity<PaymentMethod> createPaymentMethod(@Valid @RequestBody PaymentMethod paymentMethod) {
        PaymentMethod createdMethod = paymentMethodService.createPaymentMethod(paymentMethod);
        return ResponseEntity.status(201).body(createdMethod); // HTTP 201 Created
    }

    // 2. Get all payment methods
    @GetMapping
    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethods() {
        List<PaymentMethod> paymentMethods = paymentMethodService.getAllPaymentMethods();
        return ResponseEntity.ok(paymentMethods); // HTTP 200 OK
    }

    // 3. Get payment method by ID
    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethod> getPaymentMethodById(@PathVariable Long id) {
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(id);
        return ResponseEntity.ok(paymentMethod); // HTTP 200 OK
    }

    // 4. Update a payment method
    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethod> updatePaymentMethod(@PathVariable Long id,
            @Valid @RequestBody PaymentMethod paymentMethodDetails) {
        PaymentMethod updatedMethod = paymentMethodService.updatePaymentMethod(id, paymentMethodDetails);
        return ResponseEntity.ok(updatedMethod); // HTTP 200 OK
    }

    // 5. Delete a payment method
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable Long id) {
        paymentMethodService.deletePaymentMethod(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }
}
