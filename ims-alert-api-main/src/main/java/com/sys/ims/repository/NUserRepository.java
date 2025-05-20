package com.sys.ims.repository;

import com.sys.ims.enums.UserType;
import com.sys.ims.model.Client;
import com.sys.ims.model.NUser;
import com.sys.ims.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NUserRepository extends JpaRepository<NUser, UUID> {
    Optional<NUser> findByFullName(String username);

    boolean existsByEmail(String email);

    Optional<NUser> findByEmail(String email);

    List<NUser> findByUserTypeAndClientId(UserType type, UUID clientId);

    List<NUser> findByClientId(UUID clientId);
//
//    @Query(value = "SELECT * FROM WEB_USERS WHERE USER_TYPE=:type AND COMPANY_ID=:companyId", nativeQuery = true)
//    List<User> findByTypeAndCompany(String type, int companyId);
//
//    @Query(value = "SELECT * FROM WEB_USERS WHERE USER_TYPE=:refId", nativeQuery = true)
//    Optional<User> findUserByRefId(int refId);
//
//    @Query(value = "SELECT * FROM WEB_USERS WHERE UPPER(LOGIN_ID)=UPPER(:loginId) AND COMPANY_ID=:companyId", nativeQuery = true)
//    Optional<User> findUserByLoginIdAndCompanyId(String loginId, int companyId);
//
//    Boolean existsByRefId(String refId);

}
