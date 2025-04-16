package com.sys.ims.repository;

import com.sys.ims.model.Right;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RightRepository extends  JpaRepository<Right, Integer> {
    List<Right> findByParentIsNullOrderBySortAsc();
    Optional<Right> findTopByParentIdOrderBySortDesc(String id);
    List<Right> findByParentIdAndIdIn(String parentId,List<String> rightsId);
}
