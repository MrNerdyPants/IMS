package com.sys.ims.service;

import com.sys.ims.dto.MaintenanceDto;
import com.sys.ims.model.Maintenance;
import com.sys.ims.model.Notification;
import com.sys.ims.util.ApiResponse;

import java.util.List;


public interface MaintenanceService {

    ApiResponse saveMaintenance(MaintenanceDto dto);
    List<Maintenance> getAllMaintenance();
    List<Maintenance> getAllMaintenance(String companyId);
    Maintenance getAllMaintenanceById(String id);
    List<Maintenance> getAllCustomerMaintenance(String customerId);
    List<Maintenance> getAllEmployeeMaintenance(String employeeId);
    List<Maintenance> getAllCustomerComplaints(String customerId, String status);
    List<Maintenance> getAllEmployeeComplaints(String employeeId, String status);
    List<Notification> getAllNotificationByUser(String userId);
    List<Notification> getAllNotificationCountByUser(String userId);
    Long getAllNotificationMobileCountByUser(String userId);
    Boolean updateNotification(List<Integer> ids);

    Boolean updateStatus(String id, String status);
    Boolean markInOut(String id, String status);
    Boolean markOnHold(String id,String status,String reason);
    Boolean saveFeedback(String id,String level,String knowledge,String conveyed);
    Boolean saveClaim(MaintenanceDto dto);

}
