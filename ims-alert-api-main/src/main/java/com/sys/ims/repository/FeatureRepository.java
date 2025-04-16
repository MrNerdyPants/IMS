package com.sys.ims.repository;

import com.sys.ims.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeatureRepository extends JpaRepository<Feature, Integer> {

    List<Feature> findAllBySubCategoryOrSubCategoryIsNull(Integer id);
}
