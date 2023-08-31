package com.assessment.customer.domain.repo;

import com.assessment.customer.domain.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, Long> {
    @Query("SELECT c FROM CustomerEntity c WHERE CONCAT(c.firstName, ' ', c.lastName, ' ', c.idNumber) LIKE %?1%")
    List<CustomerEntity> search(String keyword);

    @Query("SELECT c FROM CustomerEntity c WHERE c.mobileNumber = :mobile")
    Optional<CustomerEntity> findCustomerByMobileNumber(@Param("mobile") String mobileNumber);

    @Query("SELECT c FROM CustomerEntity c WHERE c.idNumber = :idNumber")
    Optional<CustomerEntity> findCustomerByIdNumber(@Param("idNumber") String idNumber);
}
