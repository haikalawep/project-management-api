package com.HaikalArif.project_management_api.Service;

import com.HaikalArif.project_management_api.Model.User;
import com.HaikalArif.project_management_api.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
public class CustomUserDetailsService implements UserDetailsService  {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find user by email (username) because we use email as it is unique
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println("Test: " + user);

        // Return a User object with password and authorities based on role
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),  // This should be the encoded password
                AuthorityUtils.createAuthorityList("ROLE_"+user.getRole().name())  // Ensure role is correct, e.g. ROLE_ADMIN or ROLE_USER
        );

    }
}




