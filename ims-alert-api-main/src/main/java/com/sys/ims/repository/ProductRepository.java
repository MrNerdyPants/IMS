package com.sys.ims.repository;

import com.sys.ims.model.Company;
import com.sys.ims.model.Product;
import com.sys.ims.model.ProductType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByCompanyIsNullOrCompany(Company company);

    List<Product> findAllByProductTypeAndCompany(ProductType type,Company company);

    Product findByModelTitleEqualsIgnoreCaseAndCompanyId(String modelTitle, String companyId);
}
