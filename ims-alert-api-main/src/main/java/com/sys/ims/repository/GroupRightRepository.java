package com.sys.ims.repository;

import com.sys.ims.model.Group;
import com.sys.ims.model.GroupRight;
import com.sys.ims.model.Right;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import javax.transaction.Transactional;

public interface GroupRightRepository extends JpaRepository<GroupRight, Integer> {

    List<GroupRight> findByGroupIn(List<Group> groups);

    @Query(value = "SELECT * FROM ROLE_RIGHT WHERE ROLE_ID = :groupId", nativeQuery = true)
    List<GroupRight> findAllByGroupId(@Param("groupId") String groupId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM ROLE_RIGHT WHERE ROLE_ID = :groupId", nativeQuery = true)
    void deleteAllByGroup(String groupId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM ROLE_RIGHT WHERE RIGHT_ID = :rightId AND ROLE_ID = :groupId", nativeQuery = true)
    void deleteByRightIdAndGroupId(@Param("rightId") int rightId, @Param("groupId") int groupId);

}
