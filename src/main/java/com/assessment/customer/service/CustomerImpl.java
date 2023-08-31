package com.assessment.customer.service;

import com.assessment.customer.controller.model.ValidatorProcessorEnum;
import com.assessment.customer.domain.model.CustomerEntity;
import com.assessment.customer.domain.repo.CustomerRepo;
import com.assessment.customer.exception.CustomerNotFoundException;
import com.assessment.customer.exception.IdDuplicateException;
import com.assessment.customer.exception.InvalidIdNumberException;
import com.assessment.customer.exception.MobileDuplicateException;
import com.assessment.customer.util.validationFactory.ValidationFactory;
import com.assessment.customer.util.validationFactory.ValidationProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerImpl implements ICustomer {
    Logger logger = LoggerFactory.getLogger(CustomerImpl.class);

    private final CustomerRepo customerRepo;

    public CustomerImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public CustomerEntity createCustomer(CustomerEntity customer, ValidatorProcessorEnum processorType) {
        if (customer == null){
            return null;
        }
        //need to add validation
        isValidIdNumber(customer.getIdNumber(), processorType);
        isValidMobileNumber(customer.getMobileNumber());
        return customerRepo.save(customer);
    }

    @Override
    public CustomerEntity updateCustomer(Long customerId, CustomerEntity customer, ValidatorProcessorEnum processorType) {
        if (customer == null){
            return null;
        }
        //validation for id number and mobile
        isValidIdNumber(customer.getIdNumber(), processorType);
        isValidMobileNumber(customer.getMobileNumber());

        CustomerEntity customerEntity = customerRepo.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        customerEntity.setFirstName(customer.getFirstName());
        customerEntity.setLastName(customer.getLastName());
        customerEntity.setMobileNumber(customer.getMobileNumber());
        customerEntity.setIdNumber(customer.getIdNumber());
        customerEntity.setPhysicalAddress(customer.getPhysicalAddress());
        return customerRepo.save(customerEntity);
    }

    @Override
    public List<CustomerEntity> search(String keyword) {
        if(keyword != null){
            return customerRepo.search(keyword);
        }
        return customerRepo.findAll();
    }

    private void isValidIdNumber(String idNumber, ValidatorProcessorEnum processorType) {
        //check if is valid id number
        logger.debug("Validating the Id Number with {} processor", processorType);
        ValidationFactory validationFactory = new ValidationFactory();
        ValidationProcessor validationProcessor = validationFactory.createValidatorProcessor(processorType.getLabel());
        if (!validationProcessor.isValidIdNumber(idNumber)) {
            throw new InvalidIdNumberException(idNumber);
        }
        //Check if db has duplicate
        logger.debug("Duplicate Id Number check");
        Optional<CustomerEntity> customer = customerRepo.findCustomerByIdNumber(idNumber);
        if (customer.isPresent()) {
            throw new IdDuplicateException(idNumber);
        }

    }

    private void isValidMobileNumber(String mobileNumber) {
        //Check if db has duplicate
        Optional<CustomerEntity> customer = customerRepo.findCustomerByMobileNumber(mobileNumber);
        if (customer.isPresent()) {
            throw new MobileDuplicateException(mobileNumber);
        }
    }
}
