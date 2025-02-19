package com.HaikalArif.project_management_api.Controller;

import com.HaikalArif.project_management_api.Model.UserAuth;
import com.HaikalArif.project_management_api.Repository.UserAuthRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserAuth userAuth) {
        userAuth.setPassword(passwordEncoder.encode(userAuth.getPassword()));  // Encode the password
        userAuthRepository.save(userAuth);
        return ResponseEntity.ok("User registered successfully");
    }
}