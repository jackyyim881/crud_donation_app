package com.donation.service;

import com.donation.models.data.Receipt;

import java.util.List;

public interface ReceiptService {
    Receipt createReceipt(Receipt receipt);

    List<Receipt> getAllReceipts();

    Receipt getReceiptById(Integer id);

    Receipt updateReceipt(Integer id, Receipt receiptDetails);

    void deleteReceipt(Integer id);
}