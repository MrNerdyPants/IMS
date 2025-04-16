package com.sys.ims.controller;

import com.sys.ims.dto.ProductDto;
import com.sys.ims.model.Product;
import com.sys.ims.model.ProductType;
import com.sys.ims.service.ProductService;
import com.sys.ims.util.ApiResponse;
import com.sys.ims.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private Utility utility;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto) {
        ApiResponse apiResponse = productService.createProduct(productDto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-product-type/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllProductTypes(@PathVariable(name = "companyId") String companyId) {
        List<ProductType> list = productService.getAllProductTypes(companyId);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-product-type-by-nature/{natureType}/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllProductTypes(@PathVariable(name = "natureType") String natureType,
            @PathVariable(name = "companyId") String companyId) {
        List<ProductType> list = productService.getAllProductTypes(natureType, companyId);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-products-by-type/{type}/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllProductsByType(@PathVariable(name = "type") String type,
            @PathVariable(name = "companyId") String companyId) {
        List<Product> list = productService.getAllProductsByType(type, companyId);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-product/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllProducts(@PathVariable(name = "companyId") String companyId) {
        List<Product> list = productService.getAllProducts(companyId);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-product-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getProductById(@PathVariable(name = "id") String id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") String id) {
        Boolean flag = productService.deleteProduct(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

}
