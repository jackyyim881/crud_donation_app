package com.donation.controller;

import com.donation.models.data.Receipt;
import com.donation.repository.ReceiptRepository; // Assuming you have a repository to fetch Receipt data
import com.donation.service.PdfGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
public class PdfController {

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @Autowired
    private ReceiptRepository receiptRepository;

    @GetMapping("/downloadReceipt/{receiptId}")
    public ResponseEntity<InputStreamResource> downloadReceipt(@PathVariable Integer receiptId) {

        // Retrieve the Receipt entity from the database using the provided ID
        Receipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new RuntimeException("Receipt not found for ID: " + receiptId));

        // Generate the PDF using the receipt data
        ByteArrayInputStream bis = pdfGeneratorService.generateReceiptPdf(receipt);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=receipt_" + receipt.getReceiptNumber() + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
