package com.HaikalArif.project_management_api.Repository;

import com.HaikalArif.project_management_api.Model.Role;
import com.HaikalArif.project_management_api.Model.User;
import com.HaikalArif.project_management_api.Model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    // Find the user using Email
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
