package com.assessment.customer.util;

import com.assessment.customer.controller.model.CustomerRequest;
import com.assessment.customer.controller.model.CustomerResponse;
import com.assessment.customer.domain.model.CustomerEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerModelMapper {

    public static CustomerEntity mapToEntity(CustomerRequest customer){
        if (customer == null){
            return null;
        }

        CustomerEntity entity = new CustomerEntity();
        entity.setFirstName(customer.getFirstName());
        entity.setLastName(customer.getLastName());
        entity.setIdNumber(customer.getIdNumber());
        entity.setMobileNumber(customer.getMobileNumber());
        entity.setPhysicalAddress(customer.getPhysicalAddress());
        return entity;
    }

    public static CustomerResponse mapToResponse(CustomerEntity entity){
        if (entity == null){
            return null;
        }

        CustomerResponse response = new CustomerResponse();
        response.setCustomerId(entity.getCustomerId());
        response.setFirstName(entity.getFirstName());
        response.setLastName(entity.getLastName());
        response.setIdNumber(entity.getIdNumber());
        response.setMobileNumber(entity.getMobileNumber());
        response.setPhysicalAddress(entity.getPhysicalAddress());
        return response;
    }

    public static List<CustomerResponse> mapResponseList(List<CustomerEntity> customers){
        List<CustomerResponse> responses = new ArrayList<>();
        customers.forEach(c -> {
            responses.add(mapToResponse(c));
        });
        return responses;
    }
}
