package com.sys.ims.repository;

import com.sys.ims.model.Contract;
import com.sys.ims.model.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

    @Query(value = "SELECT * FROM IMS_CONTRACT WHERE COMPANY_ID=:companyId", nativeQuery = true)
    List<Contract> findAllByCompanyId(String companyId);

    @Query(value = "SELECT * FROM IMS_CONTRACT WHERE CUSTOMER_ID=:customerId", nativeQuery = true)
    Contract findFirstByCustomerId(int customerId);

    Boolean existsByCustomer(Customer customer);
}
