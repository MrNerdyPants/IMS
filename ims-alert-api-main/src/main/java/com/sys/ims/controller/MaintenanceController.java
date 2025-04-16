package com.sys.ims.controller;

import com.sys.ims.dto.MaintenanceDto;
import com.sys.ims.model.Maintenance;
import com.sys.ims.model.Notification;
import com.sys.ims.service.MaintenanceService;
import com.sys.ims.util.ApiResponse;
import com.sys.ims.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;
    @Autowired
    private Utility utility;

    @PostMapping("/save-maintenance")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveMaintenance(@RequestBody MaintenanceDto dto) {
        ApiResponse apiResponse = maintenanceService.saveMaintenance(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-maintenance")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllMaintenance() {
        List<Maintenance> list = maintenanceService.getAllMaintenance();
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",list), HttpStatus.OK);
    }

    @GetMapping("/get-maintenance-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllMaintenanceById(@PathVariable(name = "id") String id) {
        Maintenance list = maintenanceService.getAllMaintenanceById(id);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",list), HttpStatus.OK);
    }

    @GetMapping("/get-maintenance/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllMaintenance(@PathVariable(name = "companyId") String companyId) {
        List<Maintenance> list = maintenanceService.getAllMaintenance(companyId);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",list), HttpStatus.OK);
    }

    @GetMapping("/get-customer-maintenance/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllCustomerMaintenance(@PathVariable(name = "customerId") String customerId) {
        List<Maintenance> list = maintenanceService.getAllCustomerMaintenance(customerId);
        return new ResponseEntity<>(utility.buildApiResponse(list.size() >0 ? 200 : 404,"Success",list), HttpStatus.OK);
    }

    @GetMapping("/get-employee-maintenance/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllEmployeeMaintenance(@PathVariable(name = "employeeId") String employeeId) {
        List<Maintenance> list = maintenanceService.getAllEmployeeMaintenance(employeeId);
        return new ResponseEntity<>(utility.buildApiResponse(list.size() >0 ? 200 : 404,"Success",list), HttpStatus.OK);
    }

    @GetMapping("/get-customer-complaint/{customerId}/{status}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllCustomerComplaints(@PathVariable(name = "customerId") String customerId,
    @PathVariable(name = "status") String status) {
        List<Maintenance> list = maintenanceService.getAllCustomerComplaints(customerId,status);
        return new ResponseEntity<>(utility.buildApiResponse(list.size() > 0 ? 200 : 404,"Success",list), HttpStatus.OK);
    }

    @GetMapping("/get-employee-complaint/{employeeId}/{status}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllEmployeeComplaints(@PathVariable(name = "employeeId") String employeeId,
                                                      @PathVariable(name = "status") String status) {
        List<Maintenance> list = maintenanceService.getAllEmployeeComplaints(employeeId,status);
        return new ResponseEntity<>(utility.buildApiResponse(list.size() > 0 ? 200 : 404,"Success",list), HttpStatus.OK);
    }

    @GetMapping("/update-status/{id}/{status}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateStatus(@PathVariable(name = "id") String id,
                                                      @PathVariable(name = "status") String status) {
        Boolean flag = maintenanceService.updateStatus(id,status);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",flag), HttpStatus.OK);
    }
    @GetMapping("/mark-in-out/{id}/{status}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> markInOut(@PathVariable(name = "id") String id,
                                          @PathVariable(name = "status") String status) {
        Boolean flag = maintenanceService.markInOut(id,status);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",flag), HttpStatus.OK);
    }

    @GetMapping("/mark-on-hold/{id}/{status}/{reason}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> markOnHold(@PathVariable(name = "id") String id,
                                       @PathVariable(name = "status") String status,
                                       @PathVariable(name = "reason") String reason) {
        Boolean flag = maintenanceService.markOnHold(id,status,reason);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",flag), HttpStatus.OK);
    }

    @GetMapping("/save-feedback/{id}/{level}/{knowledge}/{conveyed}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveFeedback(@PathVariable(name = "id") String id,
                                       @PathVariable(name = "level") String level,
                                       @PathVariable(name = "knowledge") String knowledge,
                                       @PathVariable(name = "conveyed") String conveyed) {
        Boolean flag = maintenanceService.saveFeedback(id,level,knowledge,conveyed);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",flag), HttpStatus.OK);
    }

    @PostMapping("/save-claim")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveClaim(@RequestBody MaintenanceDto dto) {
        Boolean flag = maintenanceService.saveClaim(dto);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",flag), HttpStatus.OK);
    }

    @GetMapping("/get-notification/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getNotification(@PathVariable String id) {
        List<Notification> data = maintenanceService.getAllNotificationByUser(id);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",data), HttpStatus.OK);
    }

    @GetMapping("/get-notification-mobile-count/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getNotificationMobileCount(@PathVariable String id) {
        Long data = maintenanceService.getAllNotificationMobileCountByUser(id);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",data), HttpStatus.OK);
    }

    @GetMapping("/get-notification-count/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getNotificationCount(@PathVariable String id) {
        List<Notification> data = maintenanceService.getAllNotificationCountByUser(id);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",data), HttpStatus.OK);
    }

    @PostMapping("/update-notification")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateNotification(@RequestBody List<Integer> ids) {
        Boolean data = maintenanceService.updateNotification(ids);
        return new ResponseEntity<>(utility.buildApiResponse(200,"Success",data), HttpStatus.OK);
    }
}
