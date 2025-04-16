package com.sys.ims.repository;

import com.sys.ims.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends  JpaRepository<City, Integer> {
    List<City> findAllByCountryId(int countryId);
    List<City> findAllByStateId(int stateId);
}
