// package com.donation.models.data;

// import lombok.Getter;
// import lombok.Setter;

// import jakarta.persistence.*;
// import java.util.List;

// @Entity
// @Getter
// @Setter
// @Table(name = "donor")
// public class Donor {

// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Integer id;

// @ManyToOne
// @JoinColumn(name = "user_id", nullable = false)
// private User user;

// @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL)
// private List<Donation> donations;

// @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL)
// private List<DonorFeedback> donorFeedbacks;
// }
