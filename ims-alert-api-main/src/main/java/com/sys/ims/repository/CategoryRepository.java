package com.sys.ims.repository;

import com.sys.ims.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByParentIsNull();

    List<Category> findAllByParentIdIsNullAndIdIn(List<Integer> ids);

    List<Category> findAllByParentIdIsNotNull();

    List<Category> findAllByParent(Category category);

    @Query(value = "SELECT * FROM IMS_CATEGORY WHERE REF_CATEGORY_ID=:categoryId ORDER BY CATEGORY_NAME", nativeQuery = true)
    List<Category> findAllByParentId(String categoryId);

    List<Category> findAllByParentIdIn(List<Integer> ids);
}
