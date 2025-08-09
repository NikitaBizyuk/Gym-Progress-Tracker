package com.gymtracker.gym_progress_tracker.service;

import com.gymtracker.gym_progress_tracker.entity.User;
import com.gymtracker.gym_progress_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("=== ATTEMPTING LOGIN FOR USERNAME: " + username + " ===");

        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            System.out.println("=== USER NOT FOUND: " + username + " ===");
            throw new UsernameNotFoundException("User not found: " + username);
        }

        System.out.println("=== USER FOUND: " + user.getUsername() + " ===");
        System.out.println("=== STORED PASSWORD HASH: " + user.getPassword() + " ===");

        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(username);
        builder.password(user.getPassword());
        builder.roles("USER");

        return builder.build();
    }
}