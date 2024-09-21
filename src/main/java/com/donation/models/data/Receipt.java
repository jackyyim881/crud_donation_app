package com.donation.models.data;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "receipt")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "donation_id", nullable = false)
    private Donation donation;

    @Column(nullable = false, length = 50, name = "receipt_number")
    private String receiptNumber;

    @Column(nullable = false, name = "issued_date")
    private LocalDate issuedDate;
}
