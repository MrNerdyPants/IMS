package com.sys.ims.service;

import com.sys.ims.exception.BaseException;
import com.sys.ims.model.NDepartment;

import java.util.List;
import java.util.UUID;

public interface NDepartmentService {
    NDepartment createNDepartment(NDepartment nDepartment);
    NDepartment getNDepartmentById(UUID id) throws BaseException;
    List<NDepartment> getNDepartmentsByClient(UUID clientId) throws BaseException;
    NDepartment updateNDepartment(UUID id, NDepartment nDepartmentDetails) throws BaseException;
    void deleteNDepartment(UUID id) throws BaseException;
}
