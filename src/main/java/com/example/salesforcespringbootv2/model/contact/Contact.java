package com.example.salesforcespringbootv2.model.contact;

import com.example.salesforcespringbootv2.model.Attribute;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Contact {

    @Transient
    @JsonProperty("attributes")
    private Attribute attributes;
    @Id
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("AccountId")
    private String accountId;
    @JsonIgnore
    @JsonProperty("AccountName")
    private String accountName;
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Phone")
    private String phone;
    @JsonProperty("Email")
    private String email;

    public Contact(String id,
                   String name,
                   String accountId,
                   String title,
                   String phone,
                   String email) {
        this.id = id;
        this.name = name;
        this.accountId = accountId;
        this.title = title;
        this.phone = phone;
        this.email = email;
    }

    public Contact() {
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "attributes=" + attributes +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
