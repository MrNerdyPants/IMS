package com.sys.ims.controller;

import com.sys.ims.exception.BaseException;
import com.sys.ims.model.Branch;
import com.sys.ims.model.Client;
import com.sys.ims.service.BranchService;
import com.sys.ims.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<Branch> createBranch(@RequestBody Branch branch) {
        return ResponseEntity.status(HttpStatus.CREATED).body(branchService.createBranch(branch));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable UUID id) throws BaseException {
        return ResponseEntity.ok(branchService.getBranchById(id));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Branch>> getBranchesByClient(@PathVariable UUID clientId) throws BaseException {
        return ResponseEntity.ok(branchService.getBranchesByClient(clientId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable UUID id, @RequestBody Branch branch) throws BaseException {
        return ResponseEntity.ok(branchService.updateBranch(id, branch));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable UUID id) throws BaseException {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }
}
