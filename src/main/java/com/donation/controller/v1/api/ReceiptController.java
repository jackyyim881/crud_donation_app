package com.donation.controller.v1.api;

import com.donation.models.data.Receipt;
import com.donation.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

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
