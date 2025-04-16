package com.sys.ims.service;

import com.sys.ims.dto.ContractDto;
import com.sys.ims.dto.PurchaseDto;
import com.sys.ims.dto.SalesDto;
import com.sys.ims.model.Contract;
import com.sys.ims.model.Product;
import com.sys.ims.model.Purchase;
import com.sys.ims.model.Sales;
import com.sys.ims.util.ApiResponse;

import java.util.List;


public interface BusinessService {

    ApiResponse saveSales(SalesDto salesDto);
    List<Sales> getAllSales();
    List<Sales> getAllSalesByCompany(String id);
    Sales getSales(String id);
    Boolean deleteSales(String id);
    List<Product> getProductByCustomer(String id);
    ApiResponse savePurchase(PurchaseDto dto);
    List<Purchase> getAllPurchase();
    List<Purchase> getAllPurchaseByCompany(String id);
    Purchase getPurchase(String id);
    Boolean deletePurchase(String id);
    ApiResponse saveContract(ContractDto dto);
    List<Contract> getAllContract();
    List<Contract> getAllContractByCompany(String companyId);
    Contract getAllContractById(String id);
    Boolean deleteContract(String id);

}
