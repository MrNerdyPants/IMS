package com.sys.ims.repository;

import com.sys.ims.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query(value = "SELECT * FROM IMS_EMPLOYEE WHERE COMPANY_ID=:id", nativeQuery = true)
    List<Employee> findAllByCompanyId(String id);
}
