package com.donation.service;

import com.donation.models.data.Receipt;

import java.util.List;

import java.time.LocalDate;

public interface ReceiptService {
    Receipt createReceipt(Receipt receipt);

    List<Receipt> getAllReceipts();

    Receipt getReceiptById(Integer id);

    Receipt updateReceipt(Integer id, Receipt receiptDetails);

    Receipt saveReceipt(Receipt receipt);

    void deleteReceipt(Integer id);

    List<Receipt> getReceiptsByDateRange(LocalDate startDate, LocalDate endDate);
    // New methods
    // List<Receipt> createMultipleReceipts(List<Receipt> receipts);

    // List<Receipt> getReceiptsByDonorId(Integer donorId);

    // ByteArrayInputStream generateBulkReceiptPdf(List<Receipt> receipts);

    // Receipt updateReceiptStatus(Integer id, String status);

    // Map<String, Object> getSummaryReport(LocalDate startDate, LocalDate endDate);

    // List<Receipt> getReceiptsByDateRange(LocalDate startDate, LocalDate endDate);
}