package com.sys.ims.repository;

import com.sys.ims.model.Branch;
import com.sys.ims.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BranchRepository extends JpaRepository<Branch, UUID> {
    List<Branch> findByClient(Client client);
}
