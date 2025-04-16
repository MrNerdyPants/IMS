package com.sys.ims.controller;

import com.sys.ims.dto.ContractDto;
import com.sys.ims.dto.MaintenanceDto;
import com.sys.ims.dto.PurchaseDto;
import com.sys.ims.dto.SalesDto;
import com.sys.ims.model.*;
import com.sys.ims.service.BusinessService;
import com.sys.ims.util.ApiResponse;
import com.sys.ims.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business")
@RequiredArgsConstructor
public class BusinessController {

    @Autowired
    private BusinessService businessService;
    @Autowired
    private Utility utility;

    @PostMapping("/save-sales")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> createProduct(@RequestBody SalesDto salesDto) {
        ApiResponse apiResponse = businessService.saveSales(salesDto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-sales")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllSales() {
        List<Sales> list = businessService.getAllSales();
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",list), HttpStatus.OK);
    }

    @GetMapping("/get-sales/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllSales(@PathVariable(name = "companyId") String id) {
        List<Sales> list = businessService.getAllSalesByCompany(id);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",list), HttpStatus.OK);
    }

    @GetMapping("/get-sale/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getSales(@PathVariable(name = "id") String id) {
        Sales list = businessService.getSales(id);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",list), HttpStatus.OK);
    }

    @GetMapping("/get-product-by-customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getProductByCustomer(@PathVariable(name = "id") String id) {
        List<Product> product = businessService.getProductByCustomer(id);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",product), HttpStatus.OK);
    }

    @DeleteMapping("/delete-sales/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteSales(@PathVariable(name = "id") String id) {
        Boolean flag = businessService.deleteSales(id);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",flag), HttpStatus.OK);
    }


    @PostMapping("/save-purchase")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> createPurchase(@RequestBody PurchaseDto dto) {
        ApiResponse apiResponse = businessService.savePurchase(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-purchase")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllPurchase() {
        List<Purchase> list = businessService.getAllPurchase();
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",list), HttpStatus.OK);
    }

    @GetMapping("/get-purchase/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllPurchase(@PathVariable(name = "companyId") String id) {
        List<Purchase> list = businessService.getAllPurchaseByCompany(id);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",list), HttpStatus.OK);
    }

    @GetMapping("/get-purchase-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getPurchase(@PathVariable(name = "id") String id) {
        Purchase list = businessService.getPurchase(id);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-purchase/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deletePurchase(@PathVariable(name = "id") String id) {
        Boolean flag = businessService.deletePurchase(id);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",flag), HttpStatus.OK);
    }

    @PostMapping("/save-contract")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveContract(@RequestBody ContractDto dto) {
        ApiResponse apiResponse = businessService.saveContract(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-contract")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllContract() {
        List<Contract> list = businessService.getAllContract();
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",list), HttpStatus.OK);
    }
    @GetMapping("/get-contract-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllContractById(@PathVariable(name = "id") String id) {
        Contract contract = businessService.getAllContractById(id);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",contract), HttpStatus.OK);
    }

    @GetMapping("/get-contract-by-company/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllContractByCompany(@PathVariable(name = "id") String id) {
        List<Contract> list = businessService.getAllContractByCompany(id);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-contract/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteContract(@PathVariable(name = "id") String id) {
        Boolean flag = businessService.deleteContract(id);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",flag), HttpStatus.OK);
    }

}
