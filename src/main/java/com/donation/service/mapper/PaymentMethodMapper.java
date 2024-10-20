package com.donation.service.mapper;

import com.donation.dto.PaymentMethodDTO;
import com.donation.models.data.PaymentMethod;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodMapper {

    // Converts PaymentMethod entity to PaymentMethodDTO
    public PaymentMethodDTO toDTO(PaymentMethod paymentMethod) {
        return new PaymentMethodDTO(
                paymentMethod.getId(),
                paymentMethod.getMethodName());
    }

    // Converts PaymentMethodDTO to PaymentMethod entity
    public PaymentMethod toEntity(PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(paymentMethodDTO.id());
        paymentMethod.setMethodName(paymentMethodDTO.methodName());
        return paymentMethod;
    }
}
