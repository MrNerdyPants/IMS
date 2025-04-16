package com.sys.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sys.ims.model.Muduler;

public interface MudularRepository extends JpaRepository<Muduler, Integer> {
    @Modifying
    @Query(value = "DELETE FROM IMS_PURCHASEDETAIL_MODULER WHERE PURCHASE_DETAIL_ID=:detailId", nativeQuery = true)
    void deleteByDetailId(String detailId);
}
