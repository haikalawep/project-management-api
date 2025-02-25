package com.HaikalArif.project_management_api.Service;

import com.HaikalArif.project_management_api.Dto.UserDto;
import com.HaikalArif.project_management_api.Exception.NotFoundException;
import com.HaikalArif.project_management_api.Exception.UserAlreadyExistException;
import com.HaikalArif.project_management_api.Mapper.UserMapper;
import com.HaikalArif.project_management_api.Model.User;
import com.HaikalArif.project_management_api.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(UserDto userDto) {
        if (existingByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistException("Email already used!");
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword())); // Encode password
        User tempUser = UserMapper.toUserModel(userDto); // Convert User DTO to User model
        User createUser = save(tempUser); // Save user details
        if (createUser == null){
            throw new NotFoundException("Required field is null!");
        }
        return createUser;
    }

    // Create new user
    public User save(User user) {
        return userRepository.save(user);
    }

    // Check existing email
    public boolean existingByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Get User by ID
    public UserDto getUserById(long id) {
        Optional<User> getUser = userRepository.findById(id);
        if (getUser.isEmpty()) {
            throw new NotFoundException("Not Found User with ID " + id);
        }
        UserDto convertUser = UserMapper.toUserDto(getUser.get());
        return convertUser;
    }

    public User updateUser(long id, UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User tempUser = UserMapper.toUserModel(userDto);
        User updatedUser = update(id, tempUser);
        if (updatedUser == null) {
            throw new NotFoundException("Unsuccessful Update! No User with Id "+ id);
        }
        return updatedUser;
    }

    // Update User by ID
    public User update(long id, User user) {
        Optional<User> userUpdate = userRepository.findById(id);
        if (userUpdate.isPresent()) {
            User _user = userUpdate.get();
            _user.setName(user.getName());
            _user.setEmail(user.getEmail());
            _user.setPassword(user.getPassword());
            _user.setRole(user.getRole());
            return userRepository.save(_user);
        }
        else {
            return null;
        }
    }

    // Delete user by ID
    public String deleteById(long id) {
        Optional<User> getUser = userRepository.findById(id);
        if (getUser.isEmpty()) {
            throw new NotFoundException("User not found with id " + id);
        }
        userRepository.deleteById(id);
        return "Successfully delete User ID " + id;
    }

    // Get all user
    public List<User> getAllUsers() {
        List<User> user = new ArrayList<User>();
        userRepository.findAll().forEach(user1 -> user.add(user1));
        if (user.isEmpty()) {
            throw new NotFoundException("User not found!");
        }
        return user;
    }
}
