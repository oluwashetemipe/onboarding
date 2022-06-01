package com.example.onboarding.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "Address")
public class Address implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column
    private String homeAddress;
    @Column
    private String lga;
    @Column
    private String state;
    @Column
    private String doctor;

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
