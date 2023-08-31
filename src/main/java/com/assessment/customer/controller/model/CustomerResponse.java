package com.assessment.customer.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CustomerResponse implements Serializable {
    @JsonProperty("Customer ID")
    Long customerId;
    @JsonProperty("First Name")
    String firstName;
    @JsonProperty("Last Name")
    String lastName;
    @JsonProperty("Mobile Number")
    String mobileNumber;
    @JsonProperty("ID Number")
    String idNumber;
    @JsonProperty("Physical Address")
    String physicalAddress;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }
}
