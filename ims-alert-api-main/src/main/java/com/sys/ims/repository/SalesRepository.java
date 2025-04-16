package com.sys.ims.repository;

import com.sys.ims.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Integer> {


    @Query(value = "SELECT * FROM IMS_SALES WHERE CUSTOMER_ID=:customerId", nativeQuery = true)
    List<Sales> findAllByCustomer(String customerId);

    @Query(value = "SELECT * FROM IMS_SALES WHERE COMPANY_ID=:companyId", nativeQuery = true)
    List<Sales> findAllByCompanyId(String companyId);
}
