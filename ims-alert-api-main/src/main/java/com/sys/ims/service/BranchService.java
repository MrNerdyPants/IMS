package com.sys.ims.service;

import com.sys.ims.exception.BaseException;
import com.sys.ims.model.Branch;

import java.util.List;
import java.util.UUID;

public interface BranchService {
    Branch createBranch(Branch branch);
    Branch getBranchById(UUID id) throws BaseException;
    List<Branch> getBranchesByClient(UUID clientId) throws BaseException;
    Branch updateBranch(UUID id, Branch branchDetails) throws BaseException;
    void deleteBranch(UUID id) throws BaseException;
}
