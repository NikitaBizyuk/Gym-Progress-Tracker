package com.gymtracker.gym_progress_tracker.service;

import com.gymtracker.gym_progress_tracker.entity.User;
import com.gymtracker.gym_progress_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired  // Inject the PasswordEncoder instead of creating new one
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String email, String password, String firstName, String lastName){
        // check if username exists
        if(userRepository.existsByUsername(username)){
            throw new RuntimeException("Username already exists!");
        }

        //check if email exists
        if(userRepository.existsByEmail(email)){
            throw new RuntimeException("Email already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return userRepository.save(user);
    }

    public User loginUser(String username, String password){
        User user = userRepository.findByUsername(username).orElse(null);

        if(user != null && passwordEncoder.matches(password, user.getPassword())){
            return user;
        }

        return null;
    }

    public User getCurrentUser(String username){
        return userRepository.findByUsername(username).orElse(null);
    }
}