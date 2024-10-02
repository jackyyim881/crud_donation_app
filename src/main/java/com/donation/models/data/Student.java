package com.donation.models.data;

import java.util.Base64;

import jakarta.persistence.*;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "school", length = 100)
    private String school;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Lob
    @Column(name = "student_image", columnDefinition = "LONGBLOB")
    private byte[] studentImage;

    @Column(name = "latitude", nullable = true)
    private Double latitude;

    @Column(name = "longitude", nullable = true)
    private Double longitude;

    public Student() {
    }

    public Student(String name, int age, String school, String bio, byte[] studentImage) {
        this.name = name;
        this.age = age;
        this.school = school;
        this.bio = bio;
        this.studentImage = studentImage;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public byte[] getStudentImage() {
        return studentImage;
    }

    public void setStudentImage(byte[] studentImage) {
        this.studentImage = studentImage;
    }

    @Transient
    public String getImageBase64() {
        if (studentImage != null && studentImage.length > 0) {
            return Base64.getEncoder().encodeToString(studentImage);
        }
        return null;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
