package com.xdlr.temperaturerecord.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
public class UserRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String idNo;

    private String name;

    private String phone;

    private String company;

    private String companyAddress;

    private boolean toHubei;

    private boolean toWuhan;

    private boolean isFever;

    private boolean isNewCoronavirus;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date registerTime;

    @JsonIgnoreProperties(value = { "userRegister" })
    @OneToMany(mappedBy = "userRegister", cascade = CascadeType.ALL)
    private Set<TemperatureRecord> temperatureRecords = new HashSet<TemperatureRecord>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public boolean isToHubei() {
        return toHubei;
    }

    public void setToHubei(boolean toHubei) {
        this.toHubei = toHubei;
    }

    public boolean isToWuhan() {
        return toWuhan;
    }

    public void setToWuhan(boolean toWuhan) {
        this.toWuhan = toWuhan;
    }

    public boolean isFever() {
        return isFever;
    }

    public void setFever(boolean fever) {
        isFever = fever;
    }

    public boolean isNewCoronavirus() {
        return isNewCoronavirus;
    }

    public void setNewCoronavirus(boolean newCoronavirus) {
        isNewCoronavirus = newCoronavirus;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Set<TemperatureRecord> getTemperatureRecords() {
        return temperatureRecords;
    }

    public void setTemperatureRecords(Set<TemperatureRecord> temperatureRecords) {
        this.temperatureRecords = temperatureRecords;
    }
}
