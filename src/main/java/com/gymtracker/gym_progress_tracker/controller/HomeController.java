package com.gymtracker.gym_progress_tracker.controller;

import com.gymtracker.gym_progress_tracker.repository.UserRepository;
import com.gymtracker.gym_progress_tracker.repository.ExerciseRepository;
import com.gymtracker.gym_progress_tracker.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    public HomeController() {
        System.out.println("=== HomeController CONSTRUCTOR called ===");
    }

    @GetMapping("/")
    public String home(Model model) {

//        try {
            // Get counts from database
            long userCount = userRepository.count();
            long exerciseCount = exerciseRepository.count();
            long workoutCount = workoutRepository.count();

            // Add to model
            model.addAttribute("userCount", userCount);
            model.addAttribute("exerciseCount", exerciseCount);
            model.addAttribute("workoutCount", workoutCount);

//        } catch (Exception e) {
//            System.out.println("ERROR: " + e.getMessage());
//            model.addAttribute("message", "Error: " + e.getMessage());
//            model.addAttribute("userCount", "ERROR");
//            model.addAttribute("exerciseCount", "ERROR");
//            model.addAttribute("workoutCount", "ERROR");
//        }

        return "index";
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("=== TEST METHOD called ===");
        return "index";
    }
}