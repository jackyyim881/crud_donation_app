// package com.donation.service;

// import java.util.List;
// import org.springframework.stereotype.Service;
// import com.donation.models.data.Donation;
// import com.donation.repository.DonationRepository;

// import jakarta.persistence.EntityManager;
// import jakarta.persistence.PersistenceContext;

// @Service
// public class DonationService {
// @PersistenceContext
// private EntityManager entityManager;
// private final DonationRepository donationRepository;

// public DonationService(DonationRepository donationRepository) {
// this.donationRepository = donationRepository;
// }

// public List<Donation> getAllDonations() {
// return donationRepository.findAll();
// }
// }