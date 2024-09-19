// package com.donation.models.data;

// import jakarta.persistence.*;
// import java.sql.Date;
// import java.util.List;

// @Entity
// @Table(name = "campaign")
// public class Campaign {

// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private int id;

// @Column(name = "name", nullable = false, length = 100)
// private String name;

// @Column(name = "description", columnDefinition = "TEXT")
// private String description;

// @Column(name = "goal_amount", precision = 15, scale = 2, nullable = false)
// private double goalAmount;

// @Column(name = "start_date", nullable = false)
// private Date startDate;

// @Column(name = "end_date")
// private Date endDate;

// // One-to-Many relationship with Donation
// @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval =
// true)
// private List<Donation> donations;

// // Getters and Setters
// public int getId() {
// return id;
// }

// public void setId(int id) {
// this.id = id;
// }

// public String getName() {
// return name;
// }

// public void setName(String name) {
// this.name = name;
// }

// public String getDescription() {
// return description;
// }

// public void setDescription(String description) {
// this.description = description;
// }

// public double getGoalAmount() {
// return goalAmount;
// }

// public void setGoalAmount(double goalAmount) {
// this.goalAmount = goalAmount;
// }

// public Date getStartDate() {
// return startDate;
// }

// public void setStartDate(Date startDate) {
// this.startDate = startDate;
// }

// public Date getEndDate() {
// return endDate;
// }

// public void setEndDate(Date endDate) {
// this.endDate = endDate;
// }

// public List<Donation> getDonations() {
// return donations;
// }

// public void setDonations(List<Donation> donations) {
// this.donations = donations;
// }
// }
