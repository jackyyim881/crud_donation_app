// package com.donation.models.data;

// import lombok.Getter;
// import lombok.Setter;

// import jakarta.persistence.*;
// import java.util.List;

// @Getter
// @Setter
// @Entity
// @Table(name = "paymentmethod")
// public class PaymentMethod {

// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Integer id;

// private String name;

// @OneToMany(mappedBy = "paymentmethod")
// private List<Donation> donations;

// // Getters and Setters
// public Integer getId() {
// return id;
// }

// public void setId(Integer id) {
// this.id = id;
// }

// public String getName() {
// return name;
// }

// public void setName(String name) {
// this.name = name;
// }

// public List<Donation> getDonations() {
// return donations;
// }

// public void setDonations(List<Donation> donations) {
// this.donations = donations;
// }

// }
