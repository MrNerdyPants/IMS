package com.sys.ims.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sys.ims.model.CompanyCategory;

public interface CompanyCategoryRepository extends JpaRepository<CompanyCategory, Integer> {
    @Query(value = "SELECT * FROM IMS_COMPANY_CATEGORY WHERE COMPANY_ID=:companyId", nativeQuery = true)
    List<CompanyCategory> findAllCategoryByCompanyId(String companyId);
}
