package com.sys.ims.service;

import com.sys.ims.dto.ComplaintRequest;
import com.sys.ims.enums.ComplaintPriority;
import com.sys.ims.enums.ComplaintStatus;
import com.sys.ims.exception.BaseException;
import com.sys.ims.model.Complaint;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ComplaintService {
    Complaint createComplaint(ComplaintRequest request) throws BaseException;
    Complaint getComplaint(UUID id) throws BaseException;
    Complaint updateStatus(UUID id, ComplaintStatus status) throws BaseException;
    Complaint assignComplaint(UUID id, Integer employeeId) throws BaseException;
    List<Complaint> getCustomerComplaints(UUID customerId) throws BaseException;
    List<Complaint> getProductComplaints(Integer productId) throws BaseException;

}
