package com.assessment.customer.exception;

public class MobileDuplicateException extends RuntimeException{
    public MobileDuplicateException(String mobileNumber) {

        super(String.format("Duplicate mobile number: %s", mobileNumber));
    }
}
