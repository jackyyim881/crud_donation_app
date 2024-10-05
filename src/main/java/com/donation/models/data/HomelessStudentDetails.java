package com.donation.models.data;

import jakarta.persistence.*;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "homelessstudentdetails")
public class HomelessStudentDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currentShelter", length = 255)
    private String currentShelter;

    @Column(name = "durationOfHomelessness", length = 100)
    private String durationOfHomelessness;

    @Column(name = "emergencyContact", length = 255)
    private String emergencyContact;

    @Column(name = "specialNeeds", columnDefinition = "TEXT")
    private String specialNeeds;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonBackReference
    private Student student;

    // Constructors, Getters, Setters, etc.

    public HomelessStudentDetails() {
    }

    public HomelessStudentDetails(String currentShelter, String durationOfHomelessness, String emergencyContact,
            String specialNeeds, Student student) {
        this.currentShelter = currentShelter;
        this.durationOfHomelessness = durationOfHomelessness;
        this.emergencyContact = emergencyContact;
        this.specialNeeds = specialNeeds;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrentShelter() {
        return currentShelter;
    }

    public void setCurrentShelter(String currentShelter) {
        this.currentShelter = currentShelter;
    }

    public String getDurationOfHomelessness() {
        return durationOfHomelessness;
    }

    public void setDurationOfHomelessness(String durationOfHomelessness) {
        this.durationOfHomelessness = durationOfHomelessness;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getSpecialNeeds() {
        return specialNeeds;
    }

    public void setSpecialNeeds(String specialNeeds) {
        this.specialNeeds = specialNeeds;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
