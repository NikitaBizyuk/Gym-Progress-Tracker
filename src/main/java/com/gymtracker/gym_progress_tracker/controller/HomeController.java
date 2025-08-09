package com.gymtracker.gym_progress_tracker.controller;
import com.gymtracker.gym_progress_tracker.entity.Exercise;
import com.gymtracker.gym_progress_tracker.entity.User;
import com.gymtracker.gym_progress_tracker.repository.UserRepository;
import com.gymtracker.gym_progress_tracker.repository.ExerciseRepository;
import com.gymtracker.gym_progress_tracker.repository.WorkoutRepository;
import com.gymtracker.gym_progress_tracker.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ExerciseService exerciseService;

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

    @GetMapping("/exercises")
    public String exercises(Model model) {

        List<Exercise> exercises = exerciseService.getAllExercises();
        model.addAttribute("exercises",exercises);
        return "exercises";
    }
    @GetMapping("/debug/users")
    @ResponseBody
    public String debugUsers() {
        List<User> users = userRepository.findAll();
        StringBuilder result = new StringBuilder("Users in database:\n");

        for (User user : users) {
            result.append("Username: ").append(user.getUsername())
                    .append(", Email: ").append(user.getEmail())
                    .append(", Password Hash: ").append(user.getPassword())
                    .append("\n");
        }

        return result.toString();
    }
}