package com.sys.ims.controller;

import com.sys.ims.dto.ComplaintRequest;
import com.sys.ims.enums.ComplaintStatus;
import com.sys.ims.exception.BaseException;
import com.sys.ims.model.Complaint;
import com.sys.ims.model.NCustomer;
import com.sys.ims.service.ComplaintService;
import com.sys.ims.service.NCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @PostMapping
    public ResponseEntity<Complaint> createComplaint(@RequestBody ComplaintRequest request) throws BaseException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(complaintService.createComplaint(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Complaint> getComplaint(@PathVariable UUID id) throws BaseException {
        return ResponseEntity.ok(complaintService.getComplaint(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Complaint> updateStatus(
            @PathVariable UUID id,
            @RequestParam ComplaintStatus status) throws BaseException {
        return ResponseEntity.ok(complaintService.updateStatus(id, status));
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<Complaint> assignComplaint(
            @PathVariable UUID id,
            @RequestParam Integer employeeId) throws BaseException {
        return ResponseEntity.ok(complaintService.assignComplaint(id, employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Complaint>> getCustomerComplaints(
            @PathVariable UUID customerId) throws BaseException {
        return ResponseEntity.ok(complaintService.getCustomerComplaints(customerId));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Complaint>> getProductComplaints(
            @PathVariable Integer productId) throws BaseException {
        return ResponseEntity.ok(complaintService.getProductComplaints(productId));
    }

}
