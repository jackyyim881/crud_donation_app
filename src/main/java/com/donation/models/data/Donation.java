// package com.donation.models.data;

// import jakarta.persistence.*;

// import java.sql.Date;

// @Entity
// @Table(name = "donation")
// public class Donation {

// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private int id;

// @Column(name = "amount", nullable = false)
// private double amount;

// @Column(name = "date", nullable = false)
// private Date date;

// // Many-to-One relationship with Campaign
// @ManyToOne
// @JoinColumn(name = "campaign_id", nullable = false)
// private Campaign campaign;

// // Many-to-One relationship with Student (nullable, as it could be optional)
// @ManyToOne
// @JoinColumn(name = "student_id", nullable = true)
// private Student student;

// // Other columns such as donor_id and payment_method_id would go here as
// well.
// @Column(name = "donor_id", nullable = false)
// private Donor donor;

// @Column(name = "paym_ent_method_id", nullable = false)
// private int paymentMethodId;

// public int getId() {
// return id;
// }

// public void setId(int id) {
// this.id = id;
// }

// public double getAmount() {
// return amount;
// }

// public void setAmount(double amount) {
// this.amount = amount;
// }

// public Date getDate() {
// return date;
// }

// public void setDate(Date date) {
// this.date = date;
// }

// public Campaign getCampaign() {
// return campaign;
// }

// public void setCampaign(Campaign campaign) {
// this.campaign = campaign;
// }

// public Student getStudent() {
// return student;
// }

// public void setStudent(Student student) {
// this.student = student;
// }

// public Donor getDonor() {
// return donor;
// }

// public void setDonor(Donor donor) {
// this.donor = donor;
// }

// public int getPaymentMethodId() {
// return paymentMethodId;
// }

// public void setPaymentMethodId(int paymentMethodId) {
// this.paymentMethodId = paymentMethodId;
// }

// // Getters and Setters

// }
