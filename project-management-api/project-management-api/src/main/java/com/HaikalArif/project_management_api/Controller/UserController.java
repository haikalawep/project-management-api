package com.HaikalArif.project_management_api.Controller;

import com.HaikalArif.project_management_api.Dto.UserDto;
import com.HaikalArif.project_management_api.Exception.NotFoundException;
import com.HaikalArif.project_management_api.Exception.UserAlreadyExistException;
import com.HaikalArif.project_management_api.Mapper.UserMapper;
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

    @Autowired
    UserService userService;

    // Create new user
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/user")
    private User createUser(@Valid @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    // Get user by ID
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/api/user/{id}")
    private UserDto getUserById(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }

    // Update user
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/user/{id}")
    private User updateUser(@PathVariable("id") long id, @Valid @RequestBody UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    // Delete User
//    @DeleteMapping("/api/users/{id}")
//    private ResponseEntity<HttpStatus> deleteById(@PathVariable("id") long id) throws NotFoundException {
//        User getUser = userService.getUserById(id);
//        if (getUser == null) {
//            throw new NotFoundException("User not found with Id " + id);
//        } else {
//            userService.delete(id);
//            return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
//        }
//    }
//  Delete User
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/user/{id}")
    private String deleteById(@PathVariable("id") long id) {
        return userService.deleteById(id);
    }

     // Get all user
//    @GetMapping("/api/users")
//    private ResponseEntity<List<User>> getAllUser() {
////        List<Task> tempAllTask = TaskMapper.toTaskDto(allTask);
//        try {
//            List<User> allUser = userService.getAllUser();
//            return new ResponseEntity<>(allUser, HttpStatus.FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//    }

    // Get all user
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/api/user")
    private List<User> getAllUsers() {
        return userService.getAllUsers();
    }

}