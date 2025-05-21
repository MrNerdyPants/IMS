package com.sys.ims.repository;

import com.sys.ims.model.Client;
import com.sys.ims.model.NDesignation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NDesignationRepository extends JpaRepository<NDesignation, UUID> {

    List<NDesignation> findByClient(Client client);
}
