package com.donation.controller.v1.api;

import com.donation.models.data.Receipt;
import com.donation.repository.ReceiptRepository;
import com.donation.service.PdfGeneratorService;
import com.donation.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/receipts")
public class ReceiptController {
    @Autowired
    private PdfGeneratorService pdfGeneratorService;
    @Autowired
    private ReceiptService receiptService;
    @Autowired
    private ReceiptRepository receiptRepository;

    @GetMapping("/{receiptId}/pdf")
    public ResponseEntity<InputStreamResource> downloadReceiptPdf(@PathVariable Integer receiptId) {

        // Retrieve the Receipt entity from the database
        Receipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new RuntimeException("Receipt not found for ID: " + receiptId));

        // Generate the PDF
        ByteArrayInputStream bis = pdfGeneratorService.generateReceiptPdf(receipt);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=receipt_" + receipt.getReceiptNumber() + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    // 1. Create a new receipt
    @PostMapping
    public ResponseEntity<Receipt> createReceipt(@Valid @RequestBody Receipt receipt) {
        Receipt createdReceipt = receiptService.createReceipt(receipt);
        return ResponseEntity.status(201).body(createdReceipt); // HTTP 201 Created
    }

    // 2. Get all receipts
    @GetMapping
    public ResponseEntity<List<Receipt>> getAllReceipts() {
        List<Receipt> receipts = receiptService.getAllReceipts();
        return ResponseEntity.ok(receipts); // HTTP 200 OK
    }

    // 3. Get receipt by ID
    @GetMapping("/{id}")
    public ResponseEntity<Receipt> getReceiptById(@PathVariable Integer id) {
        Receipt receipt = receiptService.getReceiptById(id);
        return ResponseEntity.ok(receipt); // HTTP 200 OK
    }

    // 4. Update an existing receipt
    @PutMapping("/{id}")
    public ResponseEntity<Receipt> updateReceipt(@PathVariable Integer id, @Valid @RequestBody Receipt receiptDetails) {
        Receipt updatedReceipt = receiptService.updateReceipt(id, receiptDetails);
        return ResponseEntity.ok(updatedReceipt); // HTTP 200 OK
    }

    // 5. Delete a receipt
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceipt(@PathVariable Integer id) {
        receiptService.deleteReceipt(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }
}
