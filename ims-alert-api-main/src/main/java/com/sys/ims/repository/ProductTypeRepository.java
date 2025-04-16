package com.sys.ims.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sys.ims.model.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {

    @Query(value = "DELETE FROM IMS_PRODUCT_TYPE WHERE PRODUCT_TYPE_ID=:id AND CATEGORY_ID=:categoryId", nativeQuery = true)
    void deleteByIdAndCategoryId(int id, int categoryId);

    List<ProductType> findByTypeNature(String typeNature);

}
