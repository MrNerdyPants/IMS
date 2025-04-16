package com.sys.ims.repository;

import com.sys.ims.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Integer> {
	List<State> findByCountryId(int countryId);
}
