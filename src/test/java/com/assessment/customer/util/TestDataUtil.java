package com.assessment.customer.util;

import com.assessment.customer.domain.model.CustomerEntity;

import java.util.ArrayList;
import java.util.List;

public class TestDataUtil {
    public static List<CustomerEntity> customerEntityListWithoutKeyword(){
        List<CustomerEntity> customers = new ArrayList<>();
        CustomerEntity first = new CustomerEntity();
        first.setCustomerId(1L);
        first.setFirstName("Gary");
        first.setLastName("Msibi");
        first.setIdNumber("9211191633183");
        first.setMobileNumber("0123456789");
        first.setPhysicalAddress("Old Main Road, Ramsgate, KwaZulu-Natal, South Africa, 4275");
        customers.add(first);

        CustomerEntity second = new CustomerEntity();
        second.setCustomerId(2L);
        second.setFirstName("Andile");
        second.setLastName("Venter");
        second.setIdNumber("0105135965181");
        second.setMobileNumber("0369841681");
        second.setPhysicalAddress("60759 Legodi Corner Juanitaberg, NC 3548");
        customers.add(second);

        CustomerEntity third = new CustomerEntity();
        third.setCustomerId(3L);
        third.setFirstName("Janet");
        third.setLastName("Liebenberg");
        third.setIdNumber("9905135645084");
        third.setMobileNumber("0543070006");
        third.setPhysicalAddress("061 Fortuin Falls van der Merwechester, MP 9676");
        customers.add(third);

        CustomerEntity fourth = new CustomerEntity();
        fourth.setCustomerId(4L);
        fourth.setFirstName("Siphokazi");
        fourth.setLastName("Pietersen");
        fourth.setIdNumber("9909193400085");
        fourth.setMobileNumber("0286478910");
        fourth.setPhysicalAddress("72512 Jacobus Lodge Mthombenihaven, GP 4216");
        customers.add(fourth);

        CustomerEntity fifth = new CustomerEntity();
        fifth.setCustomerId(5L);
        fifth.setFirstName("Nicola");
        fifth.setLastName("Schoeman");
        fifth.setIdNumber("9911073697087");
        fifth.setMobileNumber("0356174559");
        fifth.setPhysicalAddress("136 nkosinathi Trace Apt. 247 Lake Johannaburgh, NW 5574");
        customers.add(fifth);

        return customers;
    }

    public static List<CustomerEntity> customerEntityListForKeyword(){
        List<CustomerEntity> customers = new ArrayList<>();
        CustomerEntity first = new CustomerEntity();
        first.setCustomerId(5L);
        first.setFirstName("Nicola");
        first.setLastName("Schoeman");
        first.setIdNumber("9911073697087");
        first.setMobileNumber("0356174585");
        first.setPhysicalAddress("136 nkosinathi Trace Apt. 247 Lake Johannaburgh, NW 5574");
        customers.add(first);

        CustomerEntity second = new CustomerEntity();
        second.setCustomerId(4L);
        second.setFirstName("Siphokazi");
        second.setLastName("Pietersen");
        second.setIdNumber("9909193400085");
        second.setMobileNumber("0286478910");
        second.setPhysicalAddress("72512 Jacobus Lodge Mthombenihaven, GP 4216");
        customers.add(second);

        CustomerEntity third = new CustomerEntity();
        third.setCustomerId(3L);
        third.setFirstName("Janet");
        third.setLastName("Liebenberg");
        third.setIdNumber("9905135645084");
        third.setMobileNumber("0543070006");
        third.setPhysicalAddress("085 Fortuin Falls van der Merwechester, MP 9676");
        customers.add(third);
        return customers;
    }
}
