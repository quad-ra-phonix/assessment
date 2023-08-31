package com.assessment.customer.service;

import com.assessment.customer.controller.model.ValidatorProcessorEnum;
import com.assessment.customer.domain.model.CustomerEntity;
import com.assessment.customer.util.validationFactory.ValidationProcessor;

import java.util.List;

public interface ICustomer {
    CustomerEntity createCustomer(CustomerEntity customer, ValidatorProcessorEnum processorType);
    CustomerEntity updateCustomer(Long customerId, CustomerEntity customer, ValidatorProcessorEnum processorType);
    List<CustomerEntity> search(String keyword);
}
