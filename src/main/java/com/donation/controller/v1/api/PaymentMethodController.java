package com.donation.controller.v1.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donation.dto.PaymentMethodDTO;
import com.donation.service.PaymentMethodService;
import com.donation.service.mapper.PaymentMethodMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/payment-methods")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;
    private final PaymentMethodMapper paymentMethodMapper;

    // Constructor injection
    public PaymentMethodController(PaymentMethodService paymentMethodService, PaymentMethodMapper paymentMethodMapper) {
        this.paymentMethodService = paymentMethodService;
        this.paymentMethodMapper = paymentMethodMapper;
    }

    // 1. Create a new payment method
    @PostMapping
    public ResponseEntity<PaymentMethodDTO> createPaymentMethod(@Valid @RequestBody PaymentMethodDTO paymentMethodDTO) {
        var paymentMethod = paymentMethodMapper.toEntity(paymentMethodDTO);
        var createdMethod = paymentMethodService.createPaymentMethod(paymentMethod);
        var response = paymentMethodMapper.toDTO(createdMethod);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // HTTP 201 Created
    }

    // 2. Get all payment methods
    @GetMapping
    public ResponseEntity<List<PaymentMethodDTO>> getAllPaymentMethods() {
        List<PaymentMethodDTO> paymentMethods = paymentMethodService.getAllPaymentMethods().stream()
                .map(paymentMethodMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(paymentMethods); // HTTP 200 OK
    }

    // 3. Get payment method by ID
    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodDTO> getPaymentMethodById(@PathVariable Long id) {
        var paymentMethod = paymentMethodService.getPaymentMethodById(id);
        var response = paymentMethodMapper.toDTO(paymentMethod);
        return ResponseEntity.ok(response); // HTTP 200 OK
    }

    // 4. Update a payment method
    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethodDTO> updatePaymentMethod(@PathVariable Long id,
            @Valid @RequestBody PaymentMethodDTO paymentMethodDTO) {
        var paymentMethodDetails = paymentMethodMapper.toEntity(paymentMethodDTO);
        var updatedMethod = paymentMethodService.updatePaymentMethod(id, paymentMethodDetails);
        var response = paymentMethodMapper.toDTO(updatedMethod);
        return ResponseEntity.ok(response); // HTTP 200 OK
    }

    // 5. Delete a payment method
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable Long id) {
        paymentMethodService.deletePaymentMethod(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }
}
