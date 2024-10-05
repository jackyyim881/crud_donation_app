package com.donation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.donation.models.data.Receipt;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {
    // You can define custom query methods if needed

    List<Receipt> findByIssuedDateBetween(LocalDate startDate, LocalDate endDate);

}