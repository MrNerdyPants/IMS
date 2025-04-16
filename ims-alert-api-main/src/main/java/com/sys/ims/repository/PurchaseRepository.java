package com.sys.ims.repository;

import com.sys.ims.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    @Query(value = "SELECT * FROM IMS_PURCHASE WHERE COMPANY_ID=:companyId", nativeQuery = true)
    List<Purchase> findAllByCompanyId(String companyId);
}
