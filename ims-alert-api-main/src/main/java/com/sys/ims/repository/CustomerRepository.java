package com.sys.ims.repository;

import com.sys.ims.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query(value = "SELECT * FROM IMS_CUSTOMER WHERE COMPANY_ID=:id", nativeQuery = true)
    List<Customer> findAllByCompanyId(int id);
}
