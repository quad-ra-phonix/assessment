package com.assessment.customer.exception;

public class IdDuplicateException extends RuntimeException{
    public IdDuplicateException(String idNumber) {

        super(String.format("Duplicate id number: %s", idNumber));
    }
}
