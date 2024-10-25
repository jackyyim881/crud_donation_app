package com.donation.specifications;

import com.donation.models.data.Donation;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class DonationSpecification {

    public static Specification<Donation> hasDonorName(String donorName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get("donor").get("name")), "%" + donorName.toLowerCase() + "%");
    }

    public static Specification<Donation> hasStudentName(String studentName) {
        return (root, query, criteriaBuilder) -> studentName == null ? null
                : criteriaBuilder.like(criteriaBuilder.lower(root.get("student").get("name")),
                        "%" + studentName.toLowerCase() + "%");
    }

    public static Specification<Donation> hasAmountGreaterThan(Double amount) {
        return (root, query, criteriaBuilder) -> amount == null ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), amount);
    }

    public static Specification<Donation> hasDonationDateBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate != null && endDate != null) {
                return criteriaBuilder.between(root.get("donationDate"), startDate, endDate);
            } else if (startDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("donationDate"), startDate);
            } else if (endDate != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("donationDate"), endDate);
            } else {
                return null;
            }
        };
    }

    // Add more specifications as needed
}
