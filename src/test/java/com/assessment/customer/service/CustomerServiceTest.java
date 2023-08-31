package com.assessment.customer.service;

import com.assessment.customer.controller.model.ValidatorProcessorEnum;
import com.assessment.customer.domain.model.CustomerEntity;
import com.assessment.customer.domain.repo.CustomerRepo;
import com.assessment.customer.exception.CustomerNotFoundException;
import com.assessment.customer.exception.IdDuplicateException;
import com.assessment.customer.exception.InvalidIdNumberException;
import com.assessment.customer.exception.MobileDuplicateException;
import com.assessment.customer.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    CustomerRepo customerRepo;

    @InjectMocks
    CustomerImpl customerService;

    @Test
    public void createCustomer_simpleValidation_success() {
        //expected
        CustomerEntity saveRequest = new CustomerEntity();
        saveRequest.setCustomerId(null);
        saveRequest.setFirstName("Test");
        saveRequest.setLastName("Dummy");
        saveRequest.setIdNumber("1234567890123");
        saveRequest.setMobileNumber("0123456789");
        saveRequest.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        CustomerEntity saved = new CustomerEntity();
        saved.setCustomerId(1L);
        saved.setFirstName("Test");
        saved.setLastName("Dummy");
        saved.setIdNumber("1234567890123");
        saved.setMobileNumber("0123456789");
        saved.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");
        //mock
        when(customerRepo.save(saveRequest)).thenReturn(saved);
        when(customerRepo.findCustomerByIdNumber(saveRequest.getIdNumber())).thenReturn(Optional.empty());
        when(customerRepo.findCustomerByMobileNumber(saveRequest.getMobileNumber())).thenReturn(Optional.empty());

        CustomerEntity response = customerService.createCustomer(saveRequest, ValidatorProcessorEnum.SIMPLE);

        //assert
        assertThat(response).isNotNull();
        assertThat(response.getCustomerId()).isNotNull();
        verify(customerRepo, times(1)).save(saveRequest);
        verify(customerRepo, times(1)).findCustomerByIdNumber(saveRequest.getIdNumber());
        verify(customerRepo, times(1)).findCustomerByMobileNumber(saveRequest.getMobileNumber());
    }

    @Test
    public void createCustomer_advancedValidation_success() {
        //expected
        CustomerEntity saveRequest = new CustomerEntity();
        saveRequest.setCustomerId(null);
        saveRequest.setFirstName("Test");
        saveRequest.setLastName("Dummy");
        saveRequest.setIdNumber("9211191633183");
        saveRequest.setMobileNumber("0123456789");
        saveRequest.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        CustomerEntity saved = new CustomerEntity();
        saved.setCustomerId(1L);
        saved.setFirstName("Test");
        saved.setLastName("Dummy");
        saved.setIdNumber("9211191633183");
        saved.setMobileNumber("0123456789");
        saved.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");
        //mock
        when(customerRepo.save(saveRequest)).thenReturn(saved);
        when(customerRepo.findCustomerByIdNumber(saveRequest.getIdNumber())).thenReturn(Optional.empty());
        when(customerRepo.findCustomerByMobileNumber(saveRequest.getMobileNumber())).thenReturn(Optional.empty());

        CustomerEntity response = customerService.createCustomer(saveRequest, ValidatorProcessorEnum.ADVANCED);

        //assert
        assertThat(response).isNotNull();
        assertThat(response.getCustomerId()).isNotNull();
        verify(customerRepo, times(1)).save(saveRequest);
        verify(customerRepo, times(1)).findCustomerByIdNumber(saveRequest.getIdNumber());
        verify(customerRepo, times(1)).findCustomerByMobileNumber(saveRequest.getMobileNumber());
    }

    @Test
    public void createCustomer_nullCustomer(){

        CustomerEntity response = customerService.createCustomer(null, ValidatorProcessorEnum.SIMPLE);

        assertThat(response).isNull();
        verify(customerRepo, never()).save(any(CustomerEntity.class));
    }

    @Test
    public void createCustomer_simpleInvalidIdNumber_Exception() {
        CustomerEntity saveRequest = new CustomerEntity();
        saveRequest.setCustomerId(null);
        saveRequest.setFirstName("Test");
        saveRequest.setLastName("Dummy");
        saveRequest.setIdNumber("921119163318");
        saveRequest.setMobileNumber("0123456789");
        saveRequest.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        org.junit.jupiter.api.Assertions.assertThrows(InvalidIdNumberException.class, () -> {
            customerService.createCustomer(saveRequest, ValidatorProcessorEnum.SIMPLE);
        });

        verify(customerRepo, never()).save(any(CustomerEntity.class));
    }

    @Test
    public void createCustomer_advancedInvalidIdNumber_Exception() {
        CustomerEntity saveRequest = new CustomerEntity();
        saveRequest.setCustomerId(null);
        saveRequest.setFirstName("Test");
        saveRequest.setLastName("Dummy");
        saveRequest.setIdNumber("1234567890123");
        saveRequest.setMobileNumber("0123456789");
        saveRequest.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        org.junit.jupiter.api.Assertions.assertThrows(InvalidIdNumberException.class, () -> {
            customerService.createCustomer(saveRequest, ValidatorProcessorEnum.ADVANCED);
        });

        verify(customerRepo, never()).save(any(CustomerEntity.class));
    }

    @Test
    public void createCustomer_idNumberDuplicate_Exception() {
        CustomerEntity saveRequest = new CustomerEntity();
        saveRequest.setCustomerId(null);
        saveRequest.setFirstName("Test");
        saveRequest.setLastName("Dummy");
        saveRequest.setIdNumber("9211191633183");
        saveRequest.setMobileNumber("0123456789");
        saveRequest.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        CustomerEntity saved = new CustomerEntity();
        saved.setCustomerId(1L);
        saved.setFirstName("Test");
        saved.setLastName("Dummy");
        saved.setIdNumber("9211191633183");
        saved.setMobileNumber("0123456789");
        saved.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        when(customerRepo.findCustomerByIdNumber(saveRequest.getIdNumber())).thenReturn(Optional.of(saved));

        org.junit.jupiter.api.Assertions.assertThrows(IdDuplicateException.class, () -> {
            customerService.createCustomer(saveRequest, ValidatorProcessorEnum.SIMPLE);
        });

        verify(customerRepo, never()).save(any(CustomerEntity.class));
        verify(customerRepo, times(1)).findCustomerByIdNumber(saveRequest.getIdNumber());
    }

    @Test
    public void createCustomer_mobileNumberDuplicate_Exception() {
        CustomerEntity saveRequest = new CustomerEntity();
        saveRequest.setCustomerId(null);
        saveRequest.setFirstName("Test");
        saveRequest.setLastName("Dummy");
        saveRequest.setIdNumber("9211191633183");
        saveRequest.setMobileNumber("0123456789");
        saveRequest.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        CustomerEntity saved = new CustomerEntity();
        saved.setCustomerId(1L);
        saved.setFirstName("Test");
        saved.setLastName("Dummy");
        saved.setIdNumber("9211191633183");
        saved.setMobileNumber("0123456789");
        saved.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        when(customerRepo.findCustomerByIdNumber(saveRequest.getIdNumber())).thenReturn(Optional.empty());
        when(customerRepo.findCustomerByMobileNumber(saveRequest.getMobileNumber())).thenReturn(Optional.of(saved));

        org.junit.jupiter.api.Assertions.assertThrows(MobileDuplicateException.class, () -> {
            customerService.createCustomer(saveRequest, ValidatorProcessorEnum.SIMPLE);
        });

        verify(customerRepo, never()).save(any(CustomerEntity.class));
    }

    @Test
    public void updateCustomer_simpleValidation_success() {
        Long customerId = 1L;

        CustomerEntity saveRequest = new CustomerEntity();
        saveRequest.setCustomerId(null);
        saveRequest.setFirstName("Jonny");
        saveRequest.setLastName("Law");
        saveRequest.setIdNumber("1234567890123");
        saveRequest.setMobileNumber("0123456789");
        saveRequest.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        CustomerEntity saved = new CustomerEntity();
        saved.setCustomerId(1L);
        saved.setFirstName("Test");
        saved.setLastName("Dummy");
        saved.setIdNumber("9211191633183");
        saved.setMobileNumber("0123456789");
        saved.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        CustomerEntity updated = new CustomerEntity();
        updated.setCustomerId(1L);
        updated.setFirstName("Jonny");
        updated.setLastName("Law");
        updated.setIdNumber("1234567890123");
        updated.setMobileNumber("0123456789");
        updated.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        when(customerRepo.findCustomerByIdNumber(saveRequest.getIdNumber())).thenReturn(Optional.empty());
        when(customerRepo.findCustomerByMobileNumber(saveRequest.getMobileNumber())).thenReturn(Optional.empty());
        when(customerRepo.findById(customerId)).thenReturn(Optional.of(saved));
        when(customerRepo.save(any(CustomerEntity.class))).thenReturn(updated);


        CustomerEntity response = customerService.updateCustomer(customerId, saveRequest, ValidatorProcessorEnum.SIMPLE);

        assertThat(response).isNotNull();
        assertThat(response.getCustomerId()).isNotNull();
        assertThat(response.getFirstName()).isEqualTo(saveRequest.getFirstName());
        assertThat(response.getLastName()).isEqualTo(saveRequest.getLastName());
        verify(customerRepo, times(1)).findById(customerId);
        verify(customerRepo, times(1)).save(any(CustomerEntity.class));
        verify(customerRepo, times(1)).findCustomerByIdNumber(saveRequest.getIdNumber());
        verify(customerRepo, times(1)).findCustomerByMobileNumber(saveRequest.getMobileNumber());

    }

    @Test
    public void updateCustomer_advancedValidation_success() {
        Long customerId = 1L;

        CustomerEntity saveRequest = new CustomerEntity();
        saveRequest.setCustomerId(null);
        saveRequest.setFirstName("Jonny");
        saveRequest.setLastName("Law");
        saveRequest.setIdNumber("9211191633183");
        saveRequest.setMobileNumber("0123456789");
        saveRequest.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        CustomerEntity saved = new CustomerEntity();
        saved.setCustomerId(1L);
        saved.setFirstName("Test");
        saved.setLastName("Dummy");
        saved.setIdNumber("9211191633183");
        saved.setMobileNumber("0123456789");
        saved.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        CustomerEntity updated = new CustomerEntity();
        updated.setCustomerId(1L);
        updated.setFirstName("Jonny");
        updated.setLastName("Law");
        updated.setIdNumber("9211191633183");
        updated.setMobileNumber("0123456789");
        updated.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        when(customerRepo.findCustomerByIdNumber(saveRequest.getIdNumber())).thenReturn(Optional.empty());
        when(customerRepo.findCustomerByMobileNumber(saveRequest.getMobileNumber())).thenReturn(Optional.empty());
        when(customerRepo.findById(customerId)).thenReturn(Optional.of(saved));
        when(customerRepo.save(any(CustomerEntity.class))).thenReturn(updated);


        CustomerEntity response = customerService.updateCustomer(customerId, saveRequest, ValidatorProcessorEnum.ADVANCED);

        assertThat(response).isNotNull();
        assertThat(response.getCustomerId()).isNotNull();
        assertThat(response.getFirstName()).isEqualTo(saveRequest.getFirstName());
        assertThat(response.getLastName()).isEqualTo(saveRequest.getLastName());
        verify(customerRepo, times(1)).findById(customerId);
        verify(customerRepo, times(1)).save(any(CustomerEntity.class));
        verify(customerRepo, times(1)).findCustomerByIdNumber(saveRequest.getIdNumber());
        verify(customerRepo, times(1)).findCustomerByMobileNumber(saveRequest.getMobileNumber());

    }

    @Test
    public void updateCustomer_nullCustomer(){
        Long customerId = 1L;

        CustomerEntity response = customerService.updateCustomer(customerId, null, ValidatorProcessorEnum.SIMPLE);

        assertThat(response).isNull();
        verify(customerRepo, never()).save(any(CustomerEntity.class));
    }

    @Test
    public void updateCustomer_customerNotFound_exception() {
        Long customerId = 1L;

        CustomerEntity saveRequest = new CustomerEntity();
        saveRequest.setCustomerId(null);
        saveRequest.setFirstName("Jonny");
        saveRequest.setLastName("Law");
        saveRequest.setIdNumber("9211191633183");
        saveRequest.setMobileNumber("0123456789");
        saveRequest.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        when(customerRepo.findCustomerByIdNumber(saveRequest.getIdNumber())).thenReturn(Optional.empty());
        when(customerRepo.findCustomerByMobileNumber(saveRequest.getMobileNumber())).thenReturn(Optional.empty());
        when(customerRepo.findById(customerId)).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            customerService.updateCustomer(customerId, saveRequest, ValidatorProcessorEnum.SIMPLE);
        });

        verify(customerRepo, times(1)).findCustomerByIdNumber(saveRequest.getIdNumber());
        verify(customerRepo, times(1)).findCustomerByMobileNumber(saveRequest.getMobileNumber());
        verify(customerRepo, times(1)).findById(customerId);
    }

    @Test
    public void updateCustomer_simpleInvalidIdNumber_Exception() {
        Long customerId = 1L;

        CustomerEntity saveRequest = new CustomerEntity();
        saveRequest.setCustomerId(null);
        saveRequest.setFirstName("Jonny");
        saveRequest.setLastName("Law");
        saveRequest.setIdNumber("921119163318");
        saveRequest.setMobileNumber("0123456789");
        saveRequest.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");


        org.junit.jupiter.api.Assertions.assertThrows(InvalidIdNumberException.class, () -> {
            customerService.updateCustomer(customerId, saveRequest, ValidatorProcessorEnum.SIMPLE);
        });

        verify(customerRepo, never()).save(any(CustomerEntity.class));
    }

    @Test
    public void updateCustomer_idNumberDuplicate_exception() {
        Long customerId = 1L;

        CustomerEntity saveRequest = new CustomerEntity();
        saveRequest.setCustomerId(null);
        saveRequest.setFirstName("Jonny");
        saveRequest.setLastName("Law");
        saveRequest.setIdNumber("9211191633183");
        saveRequest.setMobileNumber("0123456789");
        saveRequest.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        CustomerEntity saved = new CustomerEntity();
        saved.setCustomerId(1L);
        saved.setFirstName("Test");
        saved.setLastName("Dummy");
        saved.setIdNumber("9211191633183");
        saved.setMobileNumber("0123456789");
        saved.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        when(customerRepo.findCustomerByIdNumber(saveRequest.getIdNumber())).thenReturn(Optional.of(saved));

        org.junit.jupiter.api.Assertions.assertThrows(IdDuplicateException.class, () -> {
            customerService.updateCustomer(customerId, saveRequest, ValidatorProcessorEnum.SIMPLE);
        });

        verify(customerRepo, never()).save(any(CustomerEntity.class));
        verify(customerRepo, times(1)).findCustomerByIdNumber(saveRequest.getIdNumber());
    }

    @Test
    public void updateCustomer_mobileNumberDuplicate_exception() {
        Long customerId = 1L;

        CustomerEntity saveRequest = new CustomerEntity();
        saveRequest.setCustomerId(null);
        saveRequest.setFirstName("Jonny");
        saveRequest.setLastName("Law");
        saveRequest.setIdNumber("9211191633183");
        saveRequest.setMobileNumber("0123456789");
        saveRequest.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        CustomerEntity saved = new CustomerEntity();
        saved.setCustomerId(1L);
        saved.setFirstName("Test");
        saved.setLastName("Dummy");
        saved.setIdNumber("9211191633183");
        saved.setMobileNumber("0123456789");
        saved.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        when(customerRepo.findCustomerByIdNumber(saveRequest.getIdNumber())).thenReturn(Optional.empty());
        when(customerRepo.findCustomerByMobileNumber(saveRequest.getMobileNumber())).thenReturn(Optional.of(saved));

        org.junit.jupiter.api.Assertions.assertThrows(MobileDuplicateException.class, () -> {
            customerService.updateCustomer(customerId, saveRequest, ValidatorProcessorEnum.SIMPLE);
        });

        verify(customerRepo, never()).save(any(CustomerEntity.class));
        verify(customerRepo, times(1)).findCustomerByIdNumber(saveRequest.getIdNumber());
        verify(customerRepo, times(1)).findCustomerByMobileNumber(saveRequest.getMobileNumber());
    }

    @Test
    public void searchCustomer_withoutKeyword_success(){
        List<CustomerEntity> customer = TestDataUtil.customerEntityListWithoutKeyword();

        when(customerRepo.findAll()).thenReturn(customer);

        List<CustomerEntity> response = customerService.search(null);

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(5);
        verify(customerRepo, never()).search(anyString());
        verify(customerRepo, times(1)).findAll();

    }

    @Test
    public void search_withKeyword_success(){
        String keyword = "85";
        List<CustomerEntity> customers = TestDataUtil.customerEntityListForKeyword();

        when(customerRepo.search(keyword)).thenReturn(customers);

        List<CustomerEntity> response = customerService.search(keyword);

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(3);
        verify(customerRepo, never()).findAll();
        verify(customerRepo, times(1)).search(keyword);
    }

}