package com.sys.ims.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.sys.ims.model.Category;
import com.sys.ims.model.Manufacturer;
import com.sys.ims.model.ManufacturerCategory;

public interface ManufacturerCategoryRepository extends JpaRepository<ManufacturerCategory, Integer> {

    ManufacturerCategory findByManufacturerAndCategory(Manufacturer manufacturer,Category category);

}
