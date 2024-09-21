package com.donation.models.data;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "donorfeedback")
public class DonorFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "donor_id", nullable = false)
    private Donor donor;

    @Lob
    private String message;

    @Column(nullable = false, name = "submitted_date")
    private LocalDate submittedDate;

}
