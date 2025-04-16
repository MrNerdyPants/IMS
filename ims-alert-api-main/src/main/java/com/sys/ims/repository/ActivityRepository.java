package com.sys.ims.repository;

import com.sys.ims.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    @Query(value = "SELECT * FROM IMS_ACTIVITY WHERE COMPANY_ID=:companyId ORDER BY PREPARED_DTE DESC", nativeQuery = true)
    List<Activity> findAllByCompanyOrderByCreatedAtDesc(String companyId);
}
