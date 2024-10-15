
package com.donation.models.data;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "donation")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "donor_id", nullable = false) // Enforce donor not null
    private Donor donor;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = true) // Student is optional
    private Student student; // Assuming a student entity

    @Column(nullable = false)
    private Double amount;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private LocalDate donationDate;

    @ManyToOne
    @JoinColumn(name = "campaign_id", nullable = true) // Campaign is optional
    private Campaign ncampaign; // Assuming a campaign entity

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod; // Assuming a payment method entity

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDate donationDate) {
        this.donationDate = donationDate;
    }

    public Campaign getNcampaign() {
        return ncampaign;
    }

    public void setNcampaign(Campaign ncampaign) {
        this.ncampaign = ncampaign;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}