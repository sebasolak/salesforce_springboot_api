package com.example.salesforcespringbootv2.model.account;

import com.example.salesforcespringbootv2.model.Attribute;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Account {
    @Transient
    @JsonProperty("attributes")
    private Attribute attributes;
    @Id
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("AnnualRevenue")
    private Long annualRevenue;
    @JsonProperty("Industry")
    private String industry;
    @JsonProperty("Phone")
    private String phone;


    public Account() {
    }

    public Account(String id,
                   String name,
                   Long annualRevenue,
                   String industry,
                   String phone) {
        this.id = id;
        this.name = name;
        this.annualRevenue = annualRevenue;
        this.industry = industry;
        this.phone = phone;
    }

    public Attribute getAttributes() {
        return attributes;
    }

    public void setAttributes(Attribute attributes) {
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAnnualRevenue() {
        return annualRevenue;
    }

    public void setAnnualRevenue(Long annualRevenue) {
        this.annualRevenue = annualRevenue;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Account{" +
                "attributes=" + attributes +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", annualRevenue=" + annualRevenue +
                ", industry='" + industry + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
