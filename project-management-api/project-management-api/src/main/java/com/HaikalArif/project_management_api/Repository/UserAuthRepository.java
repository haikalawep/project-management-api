package com.HaikalArif.project_management_api.Repository;

import com.HaikalArif.project_management_api.Model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository
//public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
//    Optional<UserAuth> findByUsername(String username);
//
//    Boolean existsByUsername(String username);
//
//    Boolean existsByEmail(String email);
//}

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    Optional<UserAuth> findByEmail(String email);
}
