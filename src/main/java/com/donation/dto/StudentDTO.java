package com.donation.dto;

import java.util.List;

public class StudentDTO {
    private Long id;
    private String name;
    private Integer age;
    private String school;
    private String bio;
    private List<HomelessStudentDetailsDTO> homelessDetails;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
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

    public List<HomelessStudentDetailsDTO> getHomelessDetails() {
        return homelessDetails;
    }

    public void setHomelessDetails(List<HomelessStudentDetailsDTO> homelessDetails) {
        this.homelessDetails = homelessDetails;
    }

}