package com.sys.ims.service.impl;

import com.sys.ims.exception.BaseException;
import com.sys.ims.model.Branch;
import com.sys.ims.model.Client;
import com.sys.ims.repository.BranchRepository;
import com.sys.ims.repository.ClientRepository;
import com.sys.ims.service.BranchService;
import com.sys.ims.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final ClientRepository clientRepository;

    @Override
    public Branch createBranch(Branch branch) {
        branch.setCreatedAt(LocalDateTime.now());
        return branchRepository.save(branch);
    }

    @Override
    public Branch getBranchById(UUID id) throws BaseException {
        return branchRepository.findById(id)
                .orElseThrow(() -> new BaseException("Branch not found with id: " + id));
    }

    @Override
    public List<Branch> getBranchesByClient(UUID clientId) throws BaseException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new BaseException("Client not found with id: " + clientId));
        return branchRepository.findByClient(client);
    }

    @Override
    public Branch updateBranch(UUID id, Branch branchDetails) throws BaseException {
        Branch branch = getBranchById(id);
        branch.setBranchName(branchDetails.getBranchName());
        branch.setAddress(branchDetails.getAddress());
        return branchRepository.save(branch);
    }

    @Override
    public void deleteBranch(UUID id) throws BaseException {
        Branch branch = getBranchById(id);
        branchRepository.delete(branch);
    }
}