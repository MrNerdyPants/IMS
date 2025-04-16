package com.sys.ims.repository;

import com.sys.ims.model.Company;
import com.sys.ims.model.IssueType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IssueTypeRepository extends JpaRepository<IssueType, Integer> {

    // List<IssueType> findAllByCompany(Company companyId);
}
