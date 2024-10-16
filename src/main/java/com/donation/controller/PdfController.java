package com.donation.controller;

import com.donation.models.data.Receipt;
import com.donation.service.PdfService;
import com.donation.service.ReceiptService;

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
    private PdfService pdfService;

    @Autowired
    private ReceiptService receiptService;

    @GetMapping("/downloadReceipt/{receiptId}")
    public ResponseEntity<InputStreamResource> downloadReceipt(@PathVariable Integer receiptId) {

        // Retrieve the Receipt entity using the service
        Receipt receipt = receiptService.getReceiptById(receiptId);

        // Generate the PDF using the receipt data
        ByteArrayInputStream bis = pdfService.generateReceiptPdf(receipt);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=receipt_" + receipt.getReceiptNumber() + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
