package com.donation.service;

import com.donation.models.data.Receipt;
import org.springframework.stereotype.Service;
import com.donation.service.impl.PdfGeneratorImpl;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class PdfService {

    private final PdfGeneratorImpl pdfGenerator;

    public PdfService(PdfGeneratorImpl pdfGenerator) {
        this.pdfGenerator = pdfGenerator;
    }

    public ByteArrayInputStream generateBulkReceiptPdf(List<Receipt> receipts) {
        return pdfGenerator.generateBulkReceiptPdf(receipts);
    }

    public ByteArrayInputStream generateReceiptPdf(Receipt receipt) {
        return pdfGenerator.generateReceiptPdf(receipt);
    }
}
