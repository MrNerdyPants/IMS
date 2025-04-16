package com.sys.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sys.ims.model.CustomerCategory;

public interface CustomerCategoryRepository extends JpaRepository<CustomerCategory, Integer>{

}
