package com.donation.models.data;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 20, name = "phone_number")
    private String phoneNumber;

    @Column(length = 255)
    private String address;

    @Lob
    private byte[] profileImage;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public User() {
    }

    public User(String firstName, String lastName, String email) {
        this.username = firstName + " " + lastName;
        this.email = email;
    }

    // @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() {
    // return roles; // Since Role implements GrantedAuthority
    // }

    // @Override
    // public boolean isAccountNonExpired() {
    // return true; // Modify as needed
    // }

    // @Override
    // public boolean isAccountNonLocked() {
    // return true; // Modify as needed
    // }

    // @Override
    // public boolean isCredentialsNonExpired() {
    // return true; // Modify as needed
    // }

    // @Override
    // public boolean isEnabled() {
    // return true; // Modify as needed
    // }

}
