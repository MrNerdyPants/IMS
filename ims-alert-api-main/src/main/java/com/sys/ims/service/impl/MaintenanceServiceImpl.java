package com.sys.ims.service.impl;

import com.sys.ims.dto.MaintenanceDto;
import com.sys.ims.model.*;
import com.sys.ims.repository.*;
import com.sys.ims.service.MaintenanceService;
import com.sys.ims.util.ApiResponse;
import com.sys.ims.util.Constants;
import com.sys.ims.util.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private CheckListListRepository checkListListRepository;
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private UserRepository usersRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private Utility utility;

    public ApiResponse saveMaintenance(MaintenanceDto dto) {
        Maintenance entity = new Maintenance();
        if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
            Optional<Maintenance> result = maintenanceRepository.findById(Integer.parseInt(dto.getId()));
            if (result.isPresent()) {
                String partReplaced = dto.getPartReplaced();
                utility.copyNonNullProperties(dto, result.get());

                if (dto.getDetail() != null) {
                    if (result.get().getDetail() == null) {
                        result.get().setDetail(new ArrayList<>());
                    }
                    for (int i = 0; i < dto.getDetail().size(); i++) {
                        String detailId = dto.getDetail().get(i).getId();
                        MaintenanceDetail temp = result.get().getDetail().stream()
                                .filter(dtl -> dtl != null && dtl.getId() != 0 && detailId != null
                                        && !detailId.isEmpty()
                                        && dtl.getId() == Integer.parseInt(detailId))
                                .findFirst().orElse(new MaintenanceDetail());
                        utility.copyNonNullProperties(dto.getDetail().get(i), temp);
                        result.get().getDetail().add(temp);
                    }
                }

                if (dto.getStaffCheckList() != null) {
                    result.get().setStaffCheckList(new ArrayList<>());
                    for (int i = 0; i < dto.getStaffCheckList().size(); i++) {
                        MaintenanceStaffCheckList temp = new MaintenanceStaffCheckList();
                        utility.copyNonNullProperties(dto.getStaffCheckList().get(i), temp);
                        result.get().getStaffCheckList().add(temp);
                    }
                }

                if (dto.getParameters() != null) {
                    result.get().setParameters(new ArrayList<>());
                    for (int i = 0; i < dto.getParameters().size(); i++) {
                        MaintenanceParameter temp = new MaintenanceParameter();
                        utility.copyNonNullProperties(dto.getParameters().get(i), temp);
                        result.get().getParameters().add(temp);
                    }
                }

                if (dto.getParts() != null) {
                    result.get().setParts(new ArrayList<>());
                    for (int i = 0; i < dto.getParts().size(); i++) {
                        MaintenancePart temp = new MaintenancePart();
                        utility.copyNonNullProperties(dto.getParts().get(i), temp);
                        result.get().getParts().add(temp);
                    }
                }

                if (dto.getEmployee() != null && !dto.getEmployee().isEmpty()) {
                    result.get().setEmployee(findEmployeeById(dto.getEmployee()));
                }

                result.get().setVisitFrom(Date.valueOf(dto.getVisitFrom()).toString());
                result.get().setVisitTo(Date.valueOf(dto.getVisitTo()).toString());
                entity = maintenanceRepository.save(result.get());

                if (entity != null && dto.getStatus().equalsIgnoreCase("done")) {
                    createNotification(entity, "done", false, "");
                } else if (entity != null && dto.getStatus().equalsIgnoreCase("assigned")) {
                    createNotification(entity, "assigned", false, "");
                }

                if (partReplaced != null && partReplaced.equalsIgnoreCase("Y")) {
                    Optional<Product> product = productRepository.findById(entity.getProduct().getId());
                    if (product.isPresent()) {
                        if (product.get().getParts() != null && product.get().getParts().size() > 0) {
                            for (ProductPart part : product.get().getParts()) {
                                if (part.getTitle().equalsIgnoreCase(entity.getPartTitle())) {
                                    // part.setDate(entity.getPartDate());
                                    part.setLife(entity.getPartLife());
                                    part.setValue(entity.getAmount());
                                }
                            }
                        }
                    }
                    productRepository.save(product.get());
                }

                return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
            }
            return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
                    null);
        } else {
            utility.copyNonNullProperties(dto, entity);
            synchronized (this) {
                Integer myCount = maintenanceRepository.countMaintenanceByCompanyId(dto.getCompanyId());
                String compNumber = String.format("%07d", ++myCount);
                entity.setComplaintNumber(compNumber);
            }
            entity.setStatus("un-assigned");
            // Save Customer Information
            entity.setCustomer(findCustomerById(dto.getCustomer()));
            entity.setProduct(findProductById(dto.getProduct()));
            entity.setCompany(findCompanyById(dto.getCompanyId()));
            entity.setCreatedBy(findUserById(dto.getCreatedBy()));
            entity.setDate(Date.valueOf(dto.getDate()).toString());
            if (dto.getDetail() != null) {
                for (int i = 0; i < dto.getDetail().size(); i++) {
                    MaintenanceDetail temp = new MaintenanceDetail();
                    utility.copyNonNullProperties(dto.getDetail().get(i), temp);
                    temp.setMaintenace(entity);
                    temp.setCheckListList(findCheckListListById(dto.getDetail().get(i).getChecklistId()));
                    entity.getDetail().add(temp);
                }
            }
            entity = maintenanceRepository.save(entity);
            if (entity != null) {
                createNotification(entity, "un-assigned", true, "");
            }
            return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
                    Utility.isNotNull(entity) ? entity.getId() : null);
        }
    }

    public List<Maintenance> getAllMaintenance() {
        List<Maintenance> sales = maintenanceRepository.findAll();
        return sales;
    }

    public Maintenance getAllMaintenanceById(String id) {
        Optional<Maintenance> maintenances = maintenanceRepository.findById(Integer.parseInt(id));
        if (maintenances.isPresent()) {
            Maintenance maintenance = maintenances.get();
            return maintenance;
        }
        return null;
    }

    public List<Maintenance> getAllCustomerComplaints(String customerId, String status) {
        List<Maintenance> maintenances = maintenanceRepository.findAllByCustomerAndStatusInOrderByIdDesc(customerId,
                status.split(" "));
        // x for (Maintenance maintenance : maintenances) {
        // if (Utility.isNotNullAndEmpty(maintenance.getCustomer()) &&
        // customerRepository.existsById(Integer.parseInt(maintenance.getCustomer()))) {
        // maintenance.setCustomerObject(customerRepository.findById(Integer.parseInt(maintenance.getCustomer())).get());
        // }
        // if (Utility.isNotNullAndEmpty(maintenance.getProduct().getId()) &&
        // productRepository.existsById(maintenance.getProduct().getId())) {
        // maintenance.setProductObject(productRepository.findById(maintenance.getProduct().getId()).get());
        // }
        // if (Utility.isNotNullAndEmpty(maintenance.getEmployee()) &&
        // employeeRepository.existsById(Integer.parseInt(maintenance.getEmployee()))) {
        // maintenance.setEmployeeObject(employeeRepository.findById(Integer.parseInt(maintenance.getEmployee())).get());
        // }
        // }
        return maintenances;
    }

    public List<Maintenance> getAllEmployeeComplaints(String employeeId, String status) {
        List<Maintenance> maintenances = maintenanceRepository.findAllByEmployeeAndStatusInOrderByIdDesc(employeeId,
                status.split(" "));
        // for (Maintenance maintenance : maintenances) {
        // if (Utility.isNotNullAndEmpty(maintenance.getCustomer()) &&
        // customerRepository.existsById(Integer.parseInt(maintenance.getCustomer()))) {
        // maintenance.setCustomerObject(customerRepository.findById(Integer.parseInt(maintenance.getCustomer())).get());
        // }
        // if (Utility.isNotNullAndEmpty(maintenance.getProduct().getId()) &&
        // productRepository.existsById(maintenance.getProduct().getId())) {
        // maintenance.setProductObject(productRepository.findById(maintenance.getProduct().getId()).get());
        // }
        // if (Utility.isNotNullAndEmpty(maintenance.getEmployee()) &&
        // employeeRepository.existsById(Integer.parseInt(maintenance.getEmployee()))) {
        // maintenance.setEmployeeObject(employeeRepository.findById(Integer.parseInt(maintenance.getEmployee())).get());
        // }
        // }
        return maintenances;
    }

    public List<Maintenance> getAllMaintenance(String companyId) {
        List<Maintenance> maintenances = maintenanceRepository.findAllByCompanyIdOrderByIdDesc(companyId);
        // for (Maintenance maintenance : maintenances) {
        // // if (Utility.isNotNullAndEmpty(maintenance.getCustomer()) &&
        // //
        // customerRepository.existsById(Integer.parseInt(maintenance.getCustomer()))) {
        // //
        // maintenance.setCustomerObject(customerRepository.findById(Integer.parseInt(maintenance.getCustomer())).get());
        // // }
        // // // if (Utility.isNotNullAndEmpty(maintenance.getProduct()) &&
        // // productRepository.existsById(maintenance.getProduct())) {
        // // //
        // //
        // maintenance.setProductObject(productRepository.findById(maintenance.getProduct()).get());
        // // // }
        // // if (Utility.isNotNullAndEmpty(maintenance.getCustomer()) &&
        // // contractRepository.existsByCustomerObjectId(maintenance.getCustomer())) {
        // //
        // maintenance.setContract(contractRepository.findFirstByCustomerObjectId(Integer.parseInt(maintenance.getCustomer())));
        // // }
        // // if (Utility.isNotNullAndEmpty(maintenance.getEmployee()) &&
        // //
        // employeeRepository.existsById(Integer.parseInt(maintenance.getEmployee()))) {
        // //
        // maintenance.setEmployeeObject(employeeRepository.findById(Integer.parseInt(maintenance.getEmployee())).get());
        // // }
        // }
        return maintenances;
    }

    public List<Maintenance> getAllCustomerMaintenance(String customerId) {
        List<Maintenance> maintenances = maintenanceRepository.findAllByCustomerOrderByIdDesc(customerId);
        for (Maintenance maintenance : maintenances) {
            List<String> users = new ArrayList<>();
            // if (Utility.isNotNullAndEmpty(maintenance.getCustomer()) &&
            // customerRepository.existsById(Integer.parseInt(maintenance.getCustomer()))) {
            // maintenance.setCustomerObject(customerRepository.findById(Integer.parseInt(maintenance.getCustomer())).get());
            // }
            // if (Utility.isNotNullAndEmpty(maintenance.getEmployee()) &&
            // employeeRepository.existsById(Integer.parseInt(maintenance.getEmployee()))) {
            // maintenance.setEmployeeObject(employeeRepository.findById(Integer.parseInt(maintenance.getEmployee())).get());
            // Optional<User> user = userRepository.findByRefId(maintenance.getEmployee());
            // if (user.isPresent()) {
            // users.add(user.get().getName());
            // }
            // }
            // // if (Utility.isNotNullAndEmpty(maintenance.getProduct()) &&
            // productRepository.existsById(maintenance.getProduct())) {
            // //
            // maintenance.setProductObject(productRepository.findById(maintenance.getProduct()).get());
            // // }
            // if (Utility.isNotNullAndEmpty(maintenance.getCreatedBy()) &&
            // userRepository.existsById(Integer.parseInt(maintenance.getCreatedBy()))) {
            // users.add(userRepository.findById(Integer.parseInt(maintenance.getCreatedBy())).get().getName());
            // }
            // if (users.size() > 0) {
            // maintenance.setUsers(users);
            // }
        }
        return maintenances;
    }

    public List<Maintenance> getAllEmployeeMaintenance(String employeeId) {
        List<Maintenance> maintenances = maintenanceRepository.findAllByEmployeeOrderByIdDesc(employeeId);
        for (Maintenance maintenance : maintenances) {
            List<String> users = new ArrayList<>();
            if (Utility.isNotNullAndEmpty(maintenance.getCustomer().getId()) &&
                    customerRepository.existsById(maintenance.getCustomer().getId())) {
                maintenance.setCustomer(customerRepository.findById(maintenance.getCustomer().getId()).get());
            }
            if (Utility.isNotNullAndEmpty(maintenance.getEmployee().getId()) &&
                    employeeRepository.existsById(maintenance.getEmployee().getId())) {
                maintenance.setEmployee(employeeRepository.findById(maintenance.getEmployee().getId()).get());
                Optional<User> user = userRepository.findUserByRefId(maintenance.getEmployee().getId());
                if (user.isPresent()) {
                    users.add(user.get().getName());
                }
            }
            if (Utility.isNotNullAndEmpty(maintenance.getProduct().getId()) &&
                    productRepository.existsById(maintenance.getProduct().getId())) {
                maintenance.setProduct(productRepository.findById(maintenance.getProduct().getId()).get());
            }
            if (Utility.isNotNullAndEmpty(maintenance.getCreatedBy().getId()) &&
                    userRepository.existsById(maintenance.getCreatedBy().getId())) {
                users.add(userRepository.findById(maintenance.getCreatedBy().getId()).get().getName());
            }
            // if (users.size() > 0) {
            // maintenance.setUsers(users);
            // }
        }
        return maintenances;
    }

    public Boolean updateStatus(String id, String status) {
        Optional<Maintenance> maintenance = maintenanceRepository.findById(id);
        if (maintenance.isPresent()) {
            Maintenance entity = maintenance.get();
            createNotification(entity, status, false, "");
            maintenance.get().setStatus(status);
            if (status.equalsIgnoreCase("un-assigned")) {
                maintenance.get().setEmployee(null);
                maintenance.get().setVisitFrom(null);
                maintenance.get().setVisitTo(null);
            }
            maintenanceRepository.save(maintenance.get());
            return true;
        } else {
            return false;
        }
    }

    public Boolean markInOut(String id, String status) {
        Optional<Maintenance> maintenance = maintenanceRepository.findById(id);
        if (maintenance.isPresent()) {
            if (status.equalsIgnoreCase("in")) {
                maintenance.get().setCheckIn(Long.toString(System.currentTimeMillis()));
            } else if (status.equalsIgnoreCase("out")) {
                maintenance.get().setCheckOut(Long.toString(System.currentTimeMillis()));
            }
            createNotification(maintenance.get(), status, false, "");
            maintenanceRepository.save(maintenance.get());
            return true;
        } else {
            return false;
        }
    }

    public Boolean markOnHold(String id, String status, String reason) {
        Optional<Maintenance> maintenance = maintenanceRepository.findById(id);
        if (maintenance.isPresent()) {
            maintenance.get().setStatus(status);
            maintenance.get().setHoldReason(reason);
            createNotification(maintenance.get(), status, false, reason);
            maintenanceRepository.save(maintenance.get());
            return true;
        } else {
            return false;
        }
    }

    public Boolean saveFeedback(String id, String level, String knowledge, String conveyed) {
        Optional<Maintenance> maintenance = maintenanceRepository.findById(id);
        if (maintenance.isPresent()) {
            maintenance.get().setLevel(level);
            maintenance.get().setKnowledge(knowledge);
            maintenance.get().setConveyed(conveyed);
            maintenance.get().setStatus("completed");

            maintenanceRepository.save(maintenance.get());
            createNotification(maintenance.get(), "completed", false, "");
            return true;
        } else {
            return false;
        }
    }

    public Boolean saveClaim(MaintenanceDto dto) {
        Optional<Maintenance> maintenance = maintenanceRepository.findById(dto.getId());
        List<MaintenanceClaim> claims = new ArrayList<>();
        if (maintenance.isPresent()) {
            if (Utility.isNotNullAndEmpty(dto.getTravel())) {
                MaintenanceClaim claim = new MaintenanceClaim();
                claim.setTitle("travel");
                claim.setValue(dto.getTravel());
                claims.add(claim);
            }
            if (Utility.isNotNullAndEmpty(dto.getFuel())) {
                MaintenanceClaim claim = new MaintenanceClaim();
                claim.setTitle("fuel");
                claim.setValue(dto.getFuel());
                claims.add(claim);
            }
            if (Utility.isNotNullAndEmpty(dto.getEntertainment())) {
                MaintenanceClaim claim = new MaintenanceClaim();
                claim.setTitle("entertainment");
                claim.setValue(dto.getEntertainment());
                claims.add(claim);
            }
            if (Utility.isNotNullAndEmpty(dto.getHotelling())) {
                MaintenanceClaim claim = new MaintenanceClaim();
                claim.setTitle("hotelling");
                claim.setValue(dto.getHotelling());
                claims.add(claim);
            }
            if (Utility.isNotNullAndEmpty(dto.getOther())) {
                MaintenanceClaim claim = new MaintenanceClaim();
                claim.setTitle("other");
                claim.setValue(dto.getOther());
                claims.add(claim);
            }
            if (claims.size() > 0) {
                maintenance.get().setClaim(claims);
                maintenanceRepository.save(maintenance.get());
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void createNotification(Maintenance entity, String status, Boolean isNewEntry, String holdReason) {
        String message = "", customerName = "", employeeName = "", employeeUserId = "";
        List<Notification> notifications = new ArrayList<>();
        User createdFor = null, employeeUser = null;
        if (entity.getCustomer() != null && Utility.isNotNullAndEmpty(entity.getCustomer().getId())
                && customerRepository.existsById(entity.getCustomer().getId())) {
            Customer customer = customerRepository.findById(entity.getCustomer().getId()).get();
            createdFor = userRepository
                    .findUserByLoginIdAndCompanyId(customer.getLoginId(), customer.getCompanyId().getId()).get();
            customerName = customer.getShortName();
        }
        if (entity.getEmployee() != null && Utility.isNotNullAndEmpty(entity.getEmployee().getId())
                && employeeRepository.existsById(entity.getEmployee().getId())) {
            Employee employee = employeeRepository.findById(entity.getEmployee().getId()).get();
            employeeName = employee.getName();
            employeeUser = userRepository
                    .findUserByLoginIdAndCompanyId(employee.getLoginId(), employee.getCompany().getId()).get();
            employeeUserId = String.valueOf(employeeUser.getId());
        }
        if (isNewEntry) {
            Notification notification = new Notification();
            notification.setMessage(
                    "Your complaint has been register aginst the reference #" + entity.getComplaintNumber());
            notification.setCreatedFor(createdFor);
            notification.setCreatedBy(entity.getCreatedBy());
            notification.setCreatedAt(LocalDate.now());
            notification.setCompany(entity.getCompany());
            notification.setStatus("0");
            notifications.add(notification);
            message = customerName + " filled a complaint and reference #" + entity.getComplaintNumber();
        } else if (status.equalsIgnoreCase("in") || status.equalsIgnoreCase("out")) {
            Notification notification = new Notification();
            notification.setMessage("complaint #" + entity.getComplaintNumber() + " is marked on hold");
            notification.setCreatedFor(entity.getCreatedBy());
            notification.setCreatedBy(entity.getCreatedBy());
            notification.setCreatedAt(LocalDate.now());
            notification.setCompany(entity.getCompany());
            notification.setStatus("0");
            notifications.add(notification);
            message = "Check " + status + " marked on complaint #" + entity.getComplaintNumber() + " by "
                    + employeeName;
        } else if (status.equalsIgnoreCase("hold")) {
            message = "Complaint #" + entity.getComplaintNumber() + " marked on hold by " + employeeName
                    + " for reason: " + holdReason;
        } else if (status.equalsIgnoreCase("completed")) {
            message = "Complaint #" + entity.getComplaintNumber() + " has been completed";
        } else if (status.equalsIgnoreCase("un-assigned")) {
            if (Utility.isNotNullAndEmpty(employeeName)) {
                message = "Task #" + entity.getComplaintNumber() + " assignment is rejected by " + employeeName;
            } else {
                message = "Task #" + entity.getComplaintNumber() + " assignment is rejected by this employee";
            }
        } else {
            message = "Complaint #" + entity.getComplaintNumber() + " status has updated to " + status;
            Notification notification = new Notification();
            notification.setMessage(message);
            notification.setCreatedFor(createdFor);
            notification.setCreatedBy(entity.getCreatedBy());
            notification.setCreatedAt(LocalDate.now());
            notification.setCompany(entity.getCompany());
            notification.setStatus("0");
            notifications.add(notification);
            if (Utility.isNotNullAndEmpty(employeeUserId)) {
                Notification notification1 = new Notification();
                notification1.setMessage(message);
                notification1.setCreatedFor(employeeUser);
                notification1.setCreatedBy(entity.getCreatedBy());
                notification1.setCreatedAt(LocalDate.now());
                notification1.setCompany(entity.getCompany());
                notification1.setStatus("0");
                notifications.add(notification1);
            }

        }
        List<User> users = userRepository.findByTypeAndCompany("company", entity.getCompany().getId());
        for (User user : users) {
            Notification notification = new Notification();
            notification.setMessage(message);
            notification.setCreatedFor(user);
            notification.setCreatedBy(entity.getCreatedBy());
            notification.setCreatedAt(LocalDate.now());
            notification.setCompany(entity.getCompany());
            notification.setStatus("0");
            notifications.add(notification);
        }
        if (notifications.size() > 0) {
            notificationRepository.saveAll(notifications);
        }
    }

    @Override
    public List<Notification> getAllNotificationByUser(String userId) {
        return notificationRepository.findTop100ByCreatedForOrderByCreatedAtDesc(findUserById(userId));
    }

    @Override
    public Long getAllNotificationMobileCountByUser(String userId) {
        return notificationRepository.countAllByCreatedForAndStatus(findUserById(userId), "0");
    }

    @Override
    public List<Notification> getAllNotificationCountByUser(String userId) {
        List<Notification> notifications = new ArrayList<>();
        notifications.addAll(notificationRepository.findAllByCreatedForAndStatus(findUserById(userId), "0"));
        notifications.addAll(notificationRepository.findAllByCreatedForAndStatus(findUserById(userId), "1"));
        return notifications;
    }

    @Override
    public Boolean updateNotification(List<Integer> ids) {
        List<Notification> notifications = (List) notificationRepository.findAllById(ids);
        for (Notification notification : notifications) {
            notification.setStatus(notification.getStatus().equalsIgnoreCase("0") ? "1" : "2");
        }
        List<Notification> result = notificationRepository.saveAll(notifications);
        if (result.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    Site findSiteById(String categoryId) {
        if (categoryId != null && !categoryId.isEmpty()) {
            Optional<Site> category = siteRepository.findById(Integer.parseInt(categoryId));
            return category.get();
        }
        return null;
    }

    User findUserById(String userId) {
        if (userId != null && !userId.isEmpty()) {
            Optional<User> user = usersRepository.findById(Integer.parseInt(userId));
            return user.get();
        }
        return null;
    }

    Company findCompanyById(String companyId) {
        if (companyId != null && !companyId.isEmpty()) {
            Optional<Company> company = companyRepository.findById(Integer.parseInt(companyId));
            return company.get();
        }
        return null;
    }

    Customer findCustomerById(String companyId) {
        if (companyId != null && !companyId.isEmpty()) {
            Optional<Customer> company = customerRepository.findById(Integer.parseInt(companyId));
            return company.get();
        }
        return null;
    }

    Employee findEmployeeById(String companyId) {
        if (companyId != null && !companyId.isEmpty()) {
            Optional<Employee> company = employeeRepository.findById(Integer.parseInt(companyId));
            return company.get();
        }
        return null;
    }

    Product findProductById(String companyId) {
        if (companyId != null && !companyId.isEmpty()) {
            Optional<Product> company = productRepository.findById(Integer.parseInt(companyId));
            return company.get();
        }
        return null;
    }

    CheckListList findCheckListListById(String companyId) {
        if (companyId != null && !companyId.isEmpty()) {
            Optional<CheckListList> company = checkListListRepository.findById(Integer.parseInt(companyId));
            return company.get();
        }
        return null;
    }
}
