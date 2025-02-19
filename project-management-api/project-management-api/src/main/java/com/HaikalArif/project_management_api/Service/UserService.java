package com.HaikalArif.project_management_api.Service;

import com.HaikalArif.project_management_api.Model.User;
import com.HaikalArif.project_management_api.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    // Create new user
    public User saveOrUpdate(User user) {
        return userRepository.save(user);
    }
    // Try test for email check
//    public User existsByEmail(User user) {
//        return Optional<User> findExistingEmail = userRepository.findByEmail(user.getEmail());
//    }
    public boolean existingByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Get User by ID
    public User getUserById(long id) {
        Optional<User> getUser = userRepository.findById(id);
        if (getUser.isPresent()) {
            return getUser.get();
        }
        else {
            return null;
        }
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
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    // Get all user
    public List<User> getAllUser() {
        List<User> user = new ArrayList<User>();
        userRepository.findAll().forEach(user1 -> user.add(user1));
        return user;
    }
}
