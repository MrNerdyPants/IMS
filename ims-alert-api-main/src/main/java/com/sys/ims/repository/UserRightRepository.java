package com.sys.ims.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sys.ims.model.UserRight;

public interface UserRightRepository extends JpaRepository<UserRight, Integer>{

    @Query(value = "SELECT * FROM USER_RIGHT WHERE USER_ID = :userId", nativeQuery = true)
    List<UserRight> findByUserId(@Param("userId") int userId);

}
