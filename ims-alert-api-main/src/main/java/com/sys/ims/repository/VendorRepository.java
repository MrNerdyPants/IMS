package com.sys.ims.repository;

import com.sys.ims.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {

    @Query(value = "SELECT * FROM IMS_VENDOR WHERE COMPANY_ID=:id", nativeQuery = true)
    List<Vendor> findAllByCompanyId(String id);

}
