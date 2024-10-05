package com.donation.service.impl;

import com.donation.models.data.Receipt;
import com.donation.repository.ReceiptRepository;
import com.donation.service.PdfGeneratorService;
import com.donation.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @Override
    public Receipt createReceipt(Receipt receipt) {
        return receiptRepository.save(receipt);
    }

    @Override
    public List<Receipt> getAllReceipts() {
        return receiptRepository.findAll();
    }

    @Override
    public Receipt getReceiptById(Integer id) {
        return receiptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receipt not found with id: " + id));
    }

    @Override
    public Receipt updateReceipt(Integer id, Receipt receiptDetails) {
        Receipt receipt = receiptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receipt not found with id: " + id));

        receipt.setReceiptNumber(receiptDetails.getReceiptNumber());
        receipt.setIssuedDate(receiptDetails.getIssuedDate());
        receipt.setDonation(receiptDetails.getDonation());

        return receiptRepository.save(receipt);
    }

    @Override
    public void deleteReceipt(Integer id) {
        Receipt receipt = receiptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receipt not found with id: " + id));
        receiptRepository.delete(receipt);
    }

    @Override
    public List<Receipt> getReceiptsByDateRange(LocalDate startDate, LocalDate endDate) {
        return receiptRepository.findByIssuedDateBetween(startDate, endDate);
    }

}