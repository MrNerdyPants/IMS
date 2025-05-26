package com.sys.ims.repository;

import com.sys.ims.model.Complaint;
import com.sys.ims.model.Customer;
import com.sys.ims.model.NCustomer;
import com.sys.ims.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ComplaintRepository extends JpaRepository<Complaint, UUID> {

    List<Complaint> findByCustomer(NCustomer customer);

    List<Complaint> findByProduct(Product product);

}
