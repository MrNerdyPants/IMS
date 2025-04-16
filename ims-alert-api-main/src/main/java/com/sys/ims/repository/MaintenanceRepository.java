package com.sys.ims.repository;

import com.sys.ims.model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {

    @Query(value = "SELECT COUNT(*) COMPALINT FROM IMS_MAINTENANCE WHERE COMPANY_ID=:companyId", nativeQuery = true)
    Integer countMaintenanceByCompanyId(String companyId);

    @Query(value = "", nativeQuery = true)
    List<Maintenance> findAllByCustomerAndStatusInOrderByIdDesc(String customerId, String[] status);

    @Query(value = "", nativeQuery = true)
    List<Maintenance> findAllByEmployeeAndStatusInOrderByIdDesc(String employeeId, String[] status);

    @Query(value = "", nativeQuery = true)
    List<Maintenance> findAllByCustomerOrderByIdDesc(String customerId);

    @Query(value = "", nativeQuery = true)
    List<Maintenance> findAllByEmployeeOrderByIdDesc(String employeeId);

    @Query(value = "SELECT * FROM IMS_MAINTENANCE WHERE COMPANY_ID=:companyId ORDER BY 1", nativeQuery = true)
    List<Maintenance> findAllByCompanyIdOrderByIdDesc(String companyId);

    Optional<Maintenance> findById(String id);
}
