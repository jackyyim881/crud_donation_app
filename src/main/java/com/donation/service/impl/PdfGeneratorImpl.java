package com.donation.service.impl;

import com.donation.models.data.Receipt;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class PdfGeneratorImpl {

    public ByteArrayInputStream generateBulkReceiptPdf(List<Receipt> receipts) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // Add your PDF generation logic here for multiple receipts
        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream generateReceiptPdf(Receipt receipt) {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Set up fonts
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            // Company Details
            addCompanyDetails(document, titleFont);

            // Extracting details from the Receipt and Donation entities
            String receiptNumber = receipt.getReceiptNumber();
            String issuedDate = receipt.getIssuedDate().toString();
            String donorName = receipt.getDonation().getDonor().getUser().getUsername();
            String campaignName = receipt.getDonation().getNcampaign() != null
                    ? receipt.getDonation().getNcampaign().getName()
                    : "General Donation";
            Double amount = receipt.getDonation().getAmount();
            String donationDate = receipt.getDonation().getDonationDate().toString();

            // Add receipt details
            addReceiptDetails(document, normalFont, receiptNumber, issuedDate);

            // Add donor and donation details
            addDonorDetails(document, normalFont, donorName, campaignName, donationDate);

            // Add donation amount table
            addAmountTable(document, titleFont, normalFont, amount);

            // Closing message
            addClosingMessage(document, normalFont);

            document.close();

        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addCompanyDetails(Document document, Font titleFont) throws DocumentException {
        Paragraph companyDetails = new Paragraph("My Donation Company\n123 Main St, City, Country", titleFont);
        companyDetails.setAlignment(Element.ALIGN_CENTER);
        document.add(companyDetails);
        document.add(new Paragraph("\n"));
    }

    private void addReceiptDetails(Document document, Font normalFont, String receiptNumber, String issuedDate)
            throws DocumentException {
        Paragraph receiptDetails = new Paragraph(
                "Receipt Number: " + receiptNumber + "\nIssued Date: " + issuedDate, normalFont);
        receiptDetails.setAlignment(Element.ALIGN_LEFT);
        document.add(receiptDetails);
        document.add(new Paragraph("\n"));
    }

    private void addDonorDetails(Document document, Font normalFont, String donorName, String campaignName,
            String donationDate) throws DocumentException {
        Paragraph donorDetails = new Paragraph(
                "Donor Name: " + donorName + "\nCampaign: " + campaignName + "\nDonation Date: " + donationDate,
                normalFont);
        donorDetails.setAlignment(Element.ALIGN_LEFT);
        document.add(donorDetails);
        document.add(new Paragraph("\n"));
    }

    private void addAmountTable(Document document, Font titleFont, Font normalFont, Double amount)
            throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new int[] { 1, 2 });

        PdfPCell headerCell1 = new PdfPCell(new Phrase("Description", titleFont));
        headerCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell1.setBackgroundColor(com.itextpdf.text.BaseColor.LIGHT_GRAY);
        table.addCell(headerCell1);

        PdfPCell headerCell2 = new PdfPCell(new Phrase("Amount", titleFont));
        headerCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell2.setBackgroundColor(com.itextpdf.text.BaseColor.LIGHT_GRAY);
        table.addCell(headerCell2);

        PdfPCell dataCell1 = new PdfPCell(new Phrase("Donation Amount", normalFont));
        dataCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(dataCell1);

        PdfPCell dataCell2 = new PdfPCell(new Phrase(String.format("$%.2f", amount), normalFont));
        dataCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(dataCell2);

        document.add(table);
    }

    private void addClosingMessage(Document document, Font normalFont) throws DocumentException {
        Paragraph closingMessage = new Paragraph("\nThank you for your generous donation!", normalFont);
        closingMessage.setAlignment(Element.ALIGN_CENTER);
        document.add(closingMessage);
    }
}
