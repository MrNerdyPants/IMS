package com.sys.ims.repository;

import com.sys.ims.model.Client;
import com.sys.ims.model.NDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NDepartmentRepository extends JpaRepository<NDepartment, UUID> {

    List<NDepartment> findByClient(Client client);
}
