package com.sys.ims.service;

import com.sys.ims.exception.BaseException;
import com.sys.ims.model.NCustomer;

import java.util.List;
import java.util.UUID;

public interface NCustomerService {
    NCustomer createNCustomer(NCustomer nCustomer);
    NCustomer getNCustomerById(UUID id) throws BaseException;
    List<NCustomer> getNCustomersByClient(UUID clientId) throws BaseException;
    NCustomer updateNCustomer(UUID id, NCustomer nCustomerDetails) throws BaseException;
    void deleteNCustomer(UUID id) throws BaseException;
}
