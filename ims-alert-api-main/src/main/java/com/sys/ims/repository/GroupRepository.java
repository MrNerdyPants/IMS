package com.sys.ims.repository;

import com.sys.ims.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group,Integer> {
    List<Group> findAllByName(String name);
    
    List<Group> findAllByIdIn(List<Integer> Ids);

    
}
