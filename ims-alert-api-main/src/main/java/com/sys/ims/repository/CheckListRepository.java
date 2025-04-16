package com.sys.ims.repository;

import com.sys.ims.model.CheckList;
import com.sys.ims.model.Company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckListRepository extends JpaRepository<CheckList, Integer> {

    List<CheckList> findAllByCompany(Company company);

    @Query(value = "SELECT * FROM IMS_CHECKLIST WHERE COMPANY_ID=:companyId AND CHECKLIST_TYPE_ID=:type AND CATEGORY_ID IN (:ids)", nativeQuery = true)
    List<CheckList> findAllByCompanyIdAndTypeAndCategoryIdIn(
            @Param("companyId") String companyId,
            @Param("type") String type,
            @Param("ids") List<Integer> ids);

    List<CheckList> findAllByCompanyIdAndTypeAndCategoryId(String companyId, String type, String categoryId);
}
