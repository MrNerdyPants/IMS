package com.sys.ims.repository;

import com.sys.ims.model.Company;
import com.sys.ims.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

    List<Manufacturer> findAllByProductAndCompanyId(int id,int companyId);
    List<Manufacturer> findAllByCompany(Company company);
}
