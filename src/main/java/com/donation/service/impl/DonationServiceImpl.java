package com.donation.service.impl;

import com.donation.dto.DonationRequest;
import com.donation.exception.ResourceNotFoundException;
import com.donation.models.data.*;
import com.donation.repository.*;
import com.donation.service.DonationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DonationServiceImpl implements DonationService {

        private final DonationRepository donationRepository;
        private final DonorRepository donorRepository;
        private final CampaignRepository campaignRepository;
        private final PaymentMethodRepository paymentMethodRepository;
        private final StudentRepository studentRepository;

        // Constructor injection
        public DonationServiceImpl(DonationRepository donationRepository,
                        DonorRepository donorRepository,
                        CampaignRepository campaignRepository,
                        PaymentMethodRepository paymentMethodRepository,
                        StudentRepository studentRepository) {
                this.donationRepository = donationRepository;
                this.donorRepository = donorRepository;
                this.campaignRepository = campaignRepository;
                this.paymentMethodRepository = paymentMethodRepository;
                this.studentRepository = studentRepository;
        }

        @Override
        public Donation createDonation(DonationRequest donationRequest) {
                Donor donor = findDonorById(donationRequest.donorId());
                Campaign campaign = findCampaignById(donationRequest.campaignId());
                PaymentMethod paymentMethod = findPaymentMethodById(donationRequest.paymentMethodId());
                Student student = donationRequest.studentId() != null
                                ? findStudentById(donationRequest.studentId())
                                : null;

                Donation donation = new Donation();
                donation.setDonor(donor);
                donation.setNcampaign(campaign);
                donation.setPaymentMethod(paymentMethod);
                donation.setStudent(student);
                donation.setAmount(donationRequest.amount());
                donation.setDonationDate(donationRequest.donationDate());

                return donationRepository.save(donation);
        }

        @Override
        public List<Donation> getDonations(Double minAmount, Double maxAmount, LocalDate startDate, LocalDate endDate,
                        Long campaignId) {
                return donationRepository.findDonations(minAmount, maxAmount, startDate, endDate, campaignId,
                                campaignId);
        }

        @Override
        public List<Donation> getAllDonations() {
                return donationRepository.findAll();
        }

        @Override
        public Donation getDonationById(Long id) {
                return donationRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Donation not found with id: " + id));
        }

        @Override
        public Donation updateDonation(Long id, DonationRequest donationDetails) {
                Donation donation = getDonationById(id);

                Donor donor = findDonorById(donationDetails.donorId());
                Campaign campaign = findCampaignById(donationDetails.campaignId());
                PaymentMethod paymentMethod = findPaymentMethodById(donationDetails.paymentMethodId());
                Student student = donationDetails.studentId() != null
                                ? findStudentById(donationDetails.studentId())
                                : null;

                donation.setDonor(donor);
                donation.setNcampaign(campaign);
                donation.setPaymentMethod(paymentMethod);
                donation.setStudent(student);
                donation.setAmount(donationDetails.amount());
                donation.setDonationDate(donationDetails.donationDate());

                return donationRepository.save(donation);
        }

        @Override
        public void deleteDonation(Long id) {
                Donation donation = getDonationById(id);
                donationRepository.delete(donation);
        }

        @Override
        public void donate(Long studentId, Long paymentMethodId, Double donationAmount, Long donorId, Long campaignId) {

                Campaign campaign = campaignRepository.findById(campaignId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Campaign not found with id: " + campaignId));
                if (campaign == null) {
                        throw new IllegalArgumentException("Campaign not found.");
                }
                Student student = findStudentById(studentId);
                PaymentMethod paymentMethod = findPaymentMethodById(paymentMethodId);
                Donor donor = findDonorById(donorId);

                Donation donation = new Donation();
                donation.setStudent(student);
                donation.setPaymentMethod(paymentMethod);
                donation.setDonor(donor);
                donation.setAmount(donationAmount);
                donation.setNcampaign(findCampaignById(campaignId));
                donation.setDonationDate(LocalDate.now());

                donationRepository.save(donation);

        }

        // Helper methods to clean up redundant code

        private Donor findDonorById(Long donorId) {
                return donorRepository.findById(donorId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Donor not found with id: " + donorId));
        }

        private Campaign findCampaignById(Long campaignId) {
                return campaignRepository.findById(campaignId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Campaign not found with id: " + campaignId));
        }

        private PaymentMethod findPaymentMethodById(Long paymentMethodId) {
                return paymentMethodRepository.findById(paymentMethodId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Payment Method not found with id: " + paymentMethodId));
        }

        private Student findStudentById(Long studentId) {
                return studentRepository.findById(studentId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Student not found with id: " + studentId));
        }

        public List<CampaignAmountProjection> findCampaignIdAndAmount() {
                return donationRepository.findCampaignIdAndAmount();
        }

        public List<CampaignTotalAmountProjection> findTotalAmountByCampaign() {
                return donationRepository.findTotalAmountByCampaign();
        }

}
