package com.sys.ims.service.impl;

import com.sys.ims.dto.ComplaintRequest;
import com.sys.ims.enums.ComplaintStatus;
import com.sys.ims.exception.BaseException;
import com.sys.ims.model.*;
import com.sys.ims.repository.*;
import com.sys.ims.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final NCustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final EmployeeRepository employeeRepository;
    private final NDepartmentRepository departmentRepository;


    @Override
    public Complaint createComplaint(ComplaintRequest request) throws BaseException {
        NCustomer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new BaseException("Customer not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new BaseException("Product not found"));

        Complaint complaint = Complaint.builder()
                .customer(customer)
                .product(product)
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
                .status(ComplaintStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        return complaintRepository.save(complaint);
    }

    @Override
    public Complaint getComplaint(UUID id) throws BaseException {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new BaseException("Complaint not found"));
    }

    @Override
    public Complaint updateStatus(UUID id, ComplaintStatus status) throws BaseException {
        Complaint complaint = getComplaint(id);
        complaint.setStatus(status);
        complaint.setUpdatedAt(LocalDateTime.now());
        return complaintRepository.save(complaint);
    }

    @Override
    public Complaint assignComplaint(UUID id, Integer employeeId) throws BaseException {
        Complaint complaint = getComplaint(id);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new BaseException("Employee not found"));

        complaint.setAssignedTo(employee);
        complaint.setStatus(ComplaintStatus.ASSIGNED);
        complaint.setUpdatedAt(LocalDateTime.now());

        return complaintRepository.save(complaint);
    }

    @Override
    public List<Complaint> getCustomerComplaints(UUID customerId) throws BaseException {
        NCustomer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new BaseException("Customer not found"));
        return complaintRepository.findByCustomer(customer);
    }

    @Override
    public List<Complaint> getProductComplaints(Integer productId) throws BaseException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BaseException("Product not found"));
        return complaintRepository.findByProduct(product);
    }



}