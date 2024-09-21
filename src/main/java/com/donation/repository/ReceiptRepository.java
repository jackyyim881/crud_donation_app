package com.donation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.donation.models.data.Receipt;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {
    // You can define custom query methods if needed
}