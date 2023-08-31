package com.assessment.customer.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CustomerRequest {

    @NotNull(message = "First name must not be null")
    @Size(min = 1, max = 100, message = "First name must be between 1 and 100 characters")
    @JsonProperty("First Name")
    String firstName;

    @NotNull(message = "Last name must not be null")
    @Size(min = 1, max = 100, message = "Last name must be between 1 and 100 characters")
    @JsonProperty("Last Name")
    String lastName;
    @JsonProperty("Mobile Number")
    String mobileNumber;
    @NotNull(message = "ID Number can not be null")
    @JsonProperty("ID Number")
    String idNumber;
    @JsonProperty("Physical Address")
    String physicalAddress;

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
