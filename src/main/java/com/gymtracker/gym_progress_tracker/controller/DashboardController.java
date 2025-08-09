package com.gymtracker.gym_progress_tracker.controller;

import com.gymtracker.gym_progress_tracker.entity.User;
import com.gymtracker.gym_progress_tracker.service.UserService;
import com.gymtracker.gym_progress_tracker.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private WorkoutRepository workoutRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Get current logged-in user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.getCurrentUser(username);

        if (currentUser != null) {
            long userWorkoutCount = workoutRepository.countByUser(currentUser);

            model.addAttribute("user", currentUser);
            model.addAttribute("workoutCount", userWorkoutCount);
        }

        return "dashboard";
    }
}