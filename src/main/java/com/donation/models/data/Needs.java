package com.donation.models.data;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Needs")
public class Needs implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "needDescription", length = 255, nullable = false)
    private String needDescription;

    @Column(name = "goalAmount", precision = 10, scale = 2)
    private BigDecimal goalAmount;

    @Column(name = "currentAmount", precision = 10, scale = 2, nullable = false)
    private BigDecimal currentAmount = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnore
    private Student student;

    // Constructors, Getters, Setters, etc.

    public Needs() {
    }

    public Needs(String needDescription, BigDecimal goalAmount, BigDecimal currentAmount, Student student) {
        this.needDescription = needDescription;
        this.goalAmount = goalAmount;
        this.currentAmount = currentAmount;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNeedDescription() {
        return needDescription;
    }

    public void setNeedDescription(String needDescription) {
        this.needDescription = needDescription;
    }

    public BigDecimal getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(BigDecimal goalAmount) {
        this.goalAmount = goalAmount;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}