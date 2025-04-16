package com.sys.ims.repository;

import com.sys.ims.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sys.ims.model.UserGroup;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {
    void deleteById(int id);

    List<UserGroup> findAllByUser(User user);
}
