package com.sys.ims.service;

import com.sys.ims.dto.*;
import com.sys.ims.model.Product;
import com.sys.ims.model.ProductType;
import com.sys.ims.util.ApiResponse;

import java.util.List;

public interface ProductService {

    ApiResponse createProduct(ProductDto productDto);

    List<Product> getAllProducts(String companyId);

    List<Product> getAllProductsByType(String type, String companyId);

    List<ProductType> getAllProductTypes(String companyId);

    List<ProductType> getAllProductTypes(String natureType, String companyId);

    Product getProductById(String id);

    Boolean deleteProduct(String id);

}
