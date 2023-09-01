package com.assessment.customer.controller;

import com.assessment.customer.controller.model.CustomerRequest;
import com.assessment.customer.controller.model.CustomerResponse;
import com.assessment.customer.controller.model.ValidatorProcessorEnum;
import com.assessment.customer.domain.model.CustomerEntity;
import com.assessment.customer.service.ICustomer;
import com.assessment.customer.util.CustomerModelMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@Tag(name = "Customer API")
public class CustomerController {

    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final ICustomer customerService;

    public CustomerController(ICustomer customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Search for customer",
            description = "Search for customer using keyword to search first name/last name/id Number, if no keyword is passed return all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    })
    @GetMapping(path = "/customer", produces = "application/json")
    public ResponseEntity<List<CustomerResponse>> searchCustomers(@Parameter(name = "keyword", description = "Search keyword", example = "Je")
                                                                  @Param("keyword") String keyword) {
        List<CustomerEntity> list = customerService.search(keyword);
        return new ResponseEntity<>(CustomerModelMapper.mapResponseList(list), HttpStatus.OK);
    }


    @Operation(summary = "Create new customer",
    description = "Create new customer with valid request body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping(path = "/customer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customer,
                                                           @RequestParam("processorType") ValidatorProcessorEnum processorType) {
        CustomerResponse response = CustomerModelMapper.mapToResponse(customerService.createCustomer(
                CustomerModelMapper.mapToEntity(customer), processorType));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Update customer",
    description = "Update existing customer information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PutMapping("/customer/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable("customerId") Long customerId,
                                                           @Valid @RequestBody CustomerRequest customer,
                                                           @RequestParam("processorType") ValidatorProcessorEnum processorType) {
        CustomerResponse response = CustomerModelMapper.mapToResponse(customerService.updateCustomer(customerId,
                CustomerModelMapper.mapToEntity(customer), processorType));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
