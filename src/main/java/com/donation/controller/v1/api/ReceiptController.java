package com.donation.controller.v1.api;

import com.donation.dto.ReceiptRequest;
import com.donation.dto.ReceiptResponse;
import com.donation.service.ReceiptService;
import com.donation.service.mapper.ReceiptMapperService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;
    private final ReceiptMapperService receiptMapper;

    @Autowired
    public ReceiptController(ReceiptService receiptService, ReceiptMapperService receiptMapper) {
        this.receiptService = receiptService;
        this.receiptMapper = receiptMapper;
    }

    // 1. Create a new receipt
    @PostMapping
    public ResponseEntity<ReceiptResponse> createReceipt(@Valid @RequestBody ReceiptRequest receiptRequest) {
        var receipt = receiptMapper.toEntity(receiptRequest);
        var createdReceipt = receiptService.createReceipt(receipt);
        var response = receiptMapper.toDTO(createdReceipt);
        return ResponseEntity.status(201).body(response); // HTTP 201 Created
    }

    // 2. Get all receipts
    @GetMapping
    public ResponseEntity<List<ReceiptResponse>> getAllReceipts() {
        List<ReceiptResponse> receipts = receiptService.getAllReceipts().stream()
                .map(receiptMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(receipts); // HTTP 200 OK
    }

    // 3. Get receipt by ID
    @GetMapping("/{id}")
    public ResponseEntity<ReceiptResponse> getReceiptById(@PathVariable Integer id) {
        var receipt = receiptService.getReceiptById(id);
        var response = receiptMapper.toDTO(receipt);
        return ResponseEntity.ok(response); // HTTP 200 OK
    }

    // 4. Update an existing receipt
    @PutMapping("/{id}")
    public ResponseEntity<ReceiptResponse> updateReceipt(@PathVariable Integer id,
            @Valid @RequestBody ReceiptRequest receiptRequest) {
        var receiptDetails = receiptMapper.toEntity(receiptRequest);
        var updatedReceipt = receiptService.updateReceipt(id, receiptDetails);
        var response = receiptMapper.toDTO(updatedReceipt);
        return ResponseEntity.ok(response); // HTTP 200 OK
    }

    // 5. Delete a receipt
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceipt(@PathVariable Integer id) {
        receiptService.deleteReceipt(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }

    // 6. Search receipts by date range
    @GetMapping("/search")
    public ResponseEntity<List<ReceiptResponse>> searchReceiptsByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<ReceiptResponse> receipts = receiptService.getReceiptsByDateRange(startDate, endDate).stream()
                .map(receiptMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(receipts); // HTTP 200 OK
    }
}
