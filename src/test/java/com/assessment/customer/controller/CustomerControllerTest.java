package com.assessment.customer.controller;

import com.assessment.customer.controller.model.CustomerRequest;
import com.assessment.customer.controller.model.ValidatorProcessorEnum;
import com.assessment.customer.domain.model.CustomerEntity;
import com.assessment.customer.service.ICustomer;
import com.assessment.customer.util.TestDataUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @MockBean
    private ICustomer customerService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createCustomer_success() throws Exception {
        CustomerRequest request = new CustomerRequest();
        request.setFirstName("Test");
        request.setLastName("Dummy");
        request.setIdNumber("9211191633183");
        request.setMobileNumber("0123456789");
        request.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        CustomerEntity saved = new CustomerEntity();
        saved.setCustomerId(1L);
        saved.setFirstName("Test");
        saved.setLastName("Dummy");
        saved.setIdNumber("9211191633183");
        saved.setMobileNumber("0123456789");
        saved.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        Mockito.when(customerService.createCustomer(any(CustomerEntity.class), eq(ValidatorProcessorEnum.SIMPLE))).thenReturn(saved);

        mockMvc.perform(post("/customer").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .param("processorType", "SIMPLE"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.['Customer ID']").value(saved.getCustomerId()))
                .andDo(print());
        verify(customerService, times(1)).createCustomer(any(CustomerEntity.class), eq(ValidatorProcessorEnum.SIMPLE));
    }

    @Test
    public void createCustomerAPI_nullFirstName() throws Exception {
        CustomerRequest request = new CustomerRequest();
        request.setFirstName(null);
        request.setLastName("Dummy");
        request.setIdNumber("9211191633183");
        request.setMobileNumber("0123456789");
        request.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        mockMvc.perform(post("/customer").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .param("ProcessorType", "SIMPLE"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("First name must not be null"));
    }

    @Test
    public void createCustomerAPI_nullLastName() throws Exception {
        CustomerRequest request = new CustomerRequest();
        request.setFirstName("Dummy");
        request.setLastName(null);
        request.setIdNumber("9211191633183");
        request.setMobileNumber("0123456789");
        request.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        mockMvc.perform(post("/customer").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .param("ProcessorType", "SIMPLE"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("Last name must not be null"));
    }

    @Test
    public void createCustomerAPI_nullIdNumber() throws Exception {
        CustomerRequest request = new CustomerRequest();
        request.setFirstName("Dummy");
        request.setLastName("Test");
        request.setIdNumber(null);
        request.setMobileNumber("0123456789");
        request.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        mockMvc.perform(post("/customer").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .param("processorType", "SIMPLE"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("ID Number can not be null"));
    }

    @Test
    public void updateCustomer_success() throws Exception {
        //Expected Objects
        Long customerId = 1L;
        CustomerRequest request = new CustomerRequest();
        request.setFirstName("Dummy");
        request.setLastName("Test");
        request.setIdNumber("9211191633183");
        request.setMobileNumber("0123456789");
        request.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        CustomerEntity updated = new CustomerEntity();
        updated.setCustomerId(customerId);
        updated.setFirstName("Dummy");
        updated.setLastName("Test");
        updated.setIdNumber("9211191633183");
        updated.setMobileNumber("0123456789");
        updated.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");


        Mockito.when(customerService.updateCustomer(eq(customerId), any(CustomerEntity.class), eq(ValidatorProcessorEnum.SIMPLE))).thenReturn(updated);

        mockMvc.perform(put("/customer/{customerId}", customerId).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .param("processorType", "SIMPLE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['Customer ID']").value(customerId))
                .andDo(print());
        verify(customerService, times(1)).updateCustomer(eq(customerId), any(CustomerEntity.class), eq(ValidatorProcessorEnum.SIMPLE));
    }

    @Test
    public void updateCustomer_nullFirstName() throws Exception {
        Long customerId = 1L;

        CustomerRequest request = new CustomerRequest();
        request.setFirstName(null);
        request.setLastName("Dummy");
        request.setIdNumber("9211191633183");
        request.setMobileNumber("0123456789");
        request.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        mockMvc.perform(put("/customer/{customerId}", customerId).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .param("ProcessorType", "SIMPLE"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("First name must not be null"));
    }

    @Test
    public void updateCustomer_nullLastName() throws Exception {
        Long customerId = 1L;

        CustomerRequest request = new CustomerRequest();
        request.setFirstName("Test");
        request.setLastName(null);
        request.setIdNumber("9211191633183");
        request.setMobileNumber("0123456789");
        request.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        mockMvc.perform(put("/customer/{customerId}", customerId).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .param("ProcessorType", "SIMPLE"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("Last name must not be null"));
    }

    @Test
    public void updateCustomer_nullIdNumber() throws Exception {
        Long customerId = 1L;

        CustomerRequest request = new CustomerRequest();
        request.setFirstName("Test");
        request.setLastName("Dummy");
        request.setIdNumber(null);
        request.setMobileNumber("0123456789");
        request.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");

        mockMvc.perform(put("/customer/{customerId}", customerId).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .param("ProcessorType", "SIMPLE"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("ID Number can not be null"));
    }

    @Test
    public void searchCustomer_withoutKeyword() throws Exception {
        List<CustomerEntity> customerSearch = TestDataUtil.customerEntityListWithoutKeyword();

        Mockito.when(customerService.search(null)).thenReturn(customerSearch);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/customer"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        JsonNode responseJson = objectMapper.readTree(responseBody);

        assertThat(responseJson).isNotNull();
        assertThat(responseJson.size()).isEqualTo(5);

    }

    @Test
    public void searchCustomer_withKeyword() throws Exception {
        String keyword = "85";
        List<CustomerEntity> customerSearch = TestDataUtil.customerEntityListForKeyword();

        Mockito.when(customerService.search(keyword)).thenReturn(customerSearch);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/customer?keyword=" + keyword))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        JsonNode responseJson = objectMapper.readTree(responseBody);

        assertThat(responseJson).isNotNull();
        assertThat(responseJson.size()).isEqualTo(3);
    }


}