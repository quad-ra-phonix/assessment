package com.assessment.customer.exception;

public class InvalidIdNumberException extends RuntimeException{
    public InvalidIdNumberException(String mobileNumber) {

        super(String.format("Invalid id number: %s", mobileNumber));
    }
}
