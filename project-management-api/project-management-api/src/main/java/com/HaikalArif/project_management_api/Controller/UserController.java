package com.HaikalArif.project_management_api.Controller;

import com.HaikalArif.project_management_api.Dto.UserDto;
import com.HaikalArif.project_management_api.Exception.UserNotFoundException;
import com.HaikalArif.project_management_api.Exception.UserAlreadyExistException;
import com.HaikalArif.project_management_api.Mapper.TaskMapper;
import com.HaikalArif.project_management_api.Mapper.UserMapper;
import com.HaikalArif.project_management_api.Model.Task;
import com.HaikalArif.project_management_api.Model.User;
import com.HaikalArif.project_management_api.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    // Declare and inject all the service
    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Create new user
    @PostMapping("/users")
    private ResponseEntity<User> createUser(@Valid @RequestBody UserDto userDto) throws UserAlreadyExistException {

        if (userService.existingByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistException("Email already used!");
        }

//        if (!isValidEnumValue(UserRole.class, userDto.getRole()))

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword())); // Encode password
        User tempUser = UserMapper.toUserModel(userDto); // Convert User DTO to User model
        User createUser = userService.saveOrUpdate(tempUser); // Save user details
        if (createUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } // Return Bad Request if the input does not follow format or null
        else {
            return new ResponseEntity<>(createUser, HttpStatus.CREATED);
        } // Return Created if the input successful
    }

    // Get user by ID
    @GetMapping("/users/{id}")
    private ResponseEntity<UserDto> getUserById(@PathVariable("id") long id) throws UserNotFoundException {
        User getUser = userService.getUserById(id);
        if (getUser != null) {
            UserDto getUserID = UserMapper.toUserDto(getUser);
            return new ResponseEntity<>(getUserID, HttpStatus.FOUND);
        } else {
            throw new UserNotFoundException("User not found with Id : "+ id);
        }
    }

    // Update user
    @PutMapping("/users/{id}")
    private ResponseEntity<User> updateUser(@PathVariable("id") long id, @Valid @RequestBody UserDto userDto) throws UserNotFoundException {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User tempUser = UserMapper.toUserModel(userDto);
        User updatedUser = userService.update(id, tempUser);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            throw new UserNotFoundException("Unsuccessful Update! No User with Id : "+ id);
        }
    }

    // Delete User
    @DeleteMapping("/users/{id}")
    private ResponseEntity<HttpStatus> deleteById(@PathVariable("id") long id) throws UserNotFoundException {
        User getUser = userService.getUserById(id);
        if (getUser == null) {
            throw new UserNotFoundException("User not found with Id : " + id);
        } else {
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

     // Get all user
    @GetMapping("/users")
    private ResponseEntity<List<User>> getAllUser() {
//        List<Task> tempAllTask = TaskMapper.toTaskDto(allTask);
        try {
            List<User> allUser = userService.getAllUser();
            return new ResponseEntity<>(allUser, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}