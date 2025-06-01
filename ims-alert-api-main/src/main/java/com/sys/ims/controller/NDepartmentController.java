package com.sys.ims.controller;

import com.sys.ims.exception.BaseException;
import com.sys.ims.model.NDepartment;
import com.sys.ims.model.NDesignation;
import com.sys.ims.service.NDepartmentService;
import com.sys.ims.service.NDesignationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ndepartments")
@RequiredArgsConstructor
public class NDepartmentController {

    private final NDepartmentService nDepartmentService;

    @PostMapping
    public ResponseEntity<NDepartment> createNDepartment(@RequestBody NDepartment nDepartment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(nDepartmentService.createNDepartment(nDepartment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NDepartment> getNDepartmentById(@PathVariable UUID id) throws BaseException {
        return ResponseEntity.ok(nDepartmentService.getNDepartmentById(id));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<NDepartment>> getNDepartmentsByClient(@PathVariable UUID clientId) throws BaseException {
        return ResponseEntity.ok(nDepartmentService.getNDepartmentsByClient(clientId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NDepartment> updateNDepartment(@PathVariable UUID id, @RequestBody NDepartment nDepartment) throws BaseException {
        return ResponseEntity.ok(nDepartmentService.updateNDepartment(id, nDepartment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNDepartment(@PathVariable UUID id) throws BaseException {
        nDepartmentService.deleteNDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
