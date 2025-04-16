package com.sys.ims.repository;

import com.sys.ims.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM WEB_USERS WHERE USER_TYPE=:type AND COMPANY_ID=:companyId", nativeQuery = true)
    List<User> findByTypeAndCompany(String type, int companyId);

    @Query(value = "SELECT * FROM WEB_USERS WHERE USER_TYPE=:refId", nativeQuery = true)
    Optional<User> findUserByRefId(int refId);

    @Query(value = "SELECT * FROM WEB_USERS WHERE UPPER(LOGIN_ID)=UPPER(:loginId) AND COMPANY_ID=:companyId", nativeQuery = true)
    Optional<User> findUserByLoginIdAndCompanyId(String loginId, int companyId);

    Boolean existsByRefId(String refId);

}
