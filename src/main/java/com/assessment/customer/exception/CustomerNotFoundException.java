package com.assessment.customer.exception;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(Long customerId) {

        super(String.format("Customer with Id %d not found", customerId));
    }
}
