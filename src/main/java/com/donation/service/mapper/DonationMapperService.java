package com.donation.service.mapper;

import com.donation.dto.DonationRequest;
import com.donation.dto.DonationResponse;
import com.donation.exception.ResourceNotFoundException;
import com.donation.models.data.*;
import com.donation.repository.*;
import org.springframework.stereotype.Service;

@Service
public class DonationMapperService {

    private final DonorRepository donorRepository;
    private final StudentRepository studentRepository;
    private final CampaignRepository campaignRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    public DonationMapperService(DonorRepository donorRepository, StudentRepository studentRepository,
            CampaignRepository campaignRepository, PaymentMethodRepository paymentMethodRepository) {
        this.donorRepository = donorRepository;
        this.studentRepository = studentRepository;
        this.campaignRepository = campaignRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    // Convert Donation entity to DonationResponse DTO
    public DonationResponse toDTO(Donation donation) {
        return new DonationResponse(
                donation.getId(),
                donation.getDonor().getId(),
                donation.getDonor().getUser().getUsername(),
                donation.getStudent() != null ? donation.getStudent().getId() : null,
                donation.getStudent() != null ? donation.getStudent().getName() : null,
                donation.getAmount(),
                donation.getDonationDate(),
                donation.getNcampaign() != null ? donation.getNcampaign().getId() : null,
                donation.getNcampaign() != null ? donation.getNcampaign().getName() : null,
                donation.getPaymentMethod() != null ? donation.getPaymentMethod().getId() : null,
                donation.getPaymentMethod() != null ? donation.getPaymentMethod().getMethodName() : null);
    }

    // Convert DonationRequest DTO to Donation entity
    public Donation toEntity(DonationRequest donationRequest) {
        Donor donor = donorRepository.findById(donationRequest.donorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Donor not found with id: " + donationRequest.donorId()));

        Campaign campaign = campaignRepository.findById(donationRequest.campaignId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Campaign not found with id: " + donationRequest.campaignId()));

        PaymentMethod paymentMethod = paymentMethodRepository.findById(donationRequest.paymentMethodId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment method not found with id: " + donationRequest.paymentMethodId()));

        Student student = donationRequest.studentId() != null
                ? studentRepository.findById(donationRequest.studentId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Student not found with id: " + donationRequest.studentId()))
                : null;

        Donation donation = new Donation();
        donation.setDonor(donor);
        donation.setNcampaign(campaign);
        donation.setPaymentMethod(paymentMethod);
        donation.setStudent(student);
        donation.setAmount(donationRequest.amount());
        donation.setDonationDate(donationRequest.donationDate());

        return donation;
    }
}
