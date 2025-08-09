package com.gymtracker.gym_progress_tracker.controller;
import com.gymtracker.gym_progress_tracker.entity.Exercise;
import com.gymtracker.gym_progress_tracker.entity.User;
import com.gymtracker.gym_progress_tracker.entity.Workout;
import com.gymtracker.gym_progress_tracker.entity.WorkoutExercise;
import com.gymtracker.gym_progress_tracker.service.ExerciseService;
import com.gymtracker.gym_progress_tracker.service.UserService;
import com.gymtracker.gym_progress_tracker.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.time.LocalDateTime;


@Controller
public class WorkoutController {

    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private UserService userService;
    @Autowired
    private WorkoutService workoutService;

    @GetMapping("/start")
    public String startWorkout(Model model, Authentication authentication) {
        // Get current user
        User currentUser = userService.getCurrentUser(authentication.getName());

        //Create new workout
        Workout workout = new Workout();
        workout.setUser(currentUser);
        workout.setWorkoutDate(LocalDateTime.now());
        workout.setName("Workout " + LocalDateTime.now().toLocalDate());

        // Get all exercises for selection
        List<Exercise> exercises = exerciseService.getAllExercises();

        model.addAttribute("workout", workout);
        model.addAttribute("exercises", exercises);
        model.addAttribute("user", currentUser);

        return "start";

    }

    @PostMapping("/save")
    public String saveWorkout(
            @RequestParam String workoutName,
            @RequestParam(required = false) String notes,
            @RequestParam(required = false) Integer durationMinutes,
            @RequestParam List<Long> exerciseIds,
            @RequestParam List<Integer> sets,
            @RequestParam List<Integer> reps,
            @RequestParam List<Double> weights,
            @RequestParam(required = false) List<String> exerciseNotes,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        try{
            User currentUser = userService.getCurrentUser(authentication.getName());

            // Create and save workout
            Workout workout = new Workout();
            workout.setUser(currentUser);
            workout.setName(workoutName);
            workout.setNotes(notes);
            workout.setDurationMinutes(durationMinutes);
            workout.setWorkoutDate(LocalDateTime.now());

            Workout savedWorkout = workoutService.saveWorkout(workout, exerciseIds, sets, reps, weights, exerciseNotes);

            redirectAttributes.addFlashAttribute("success",
                    "Workout saved successfully! Great job!");

            return "redirect:/workouts/" + savedWorkout.getId();

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Failed to save workout: " + e.getMessage());
            return "redirect:/start";
        }
    }

    @GetMapping("/{id}")
    public String viewWorkout(@PathVariable Long id, Model model, Authentication authentication) {
        try {
            User currentUser = userService.getCurrentUser(authentication.getName());
            Workout workout = workoutService.getWorkoutById(id);

            // Check if workout belongs to current user
            if(!workout.getUser().getId().equals(currentUser.getId())){
                return "redirect:/dashboard?error=unauthorized";
            }

            model.addAttribute("workout",workout);
            return "workout/view";
        } catch(Exception e){
            return "redirect:/dashboard?error=workoutnotfound";
        }
    }

    @GetMapping("/history")
    public String workoutHistory(Model model, Authentication authentication){
        User currentUser = userService.getCurrentUser(authentication.getName());
        List<Workout> workouts = workoutService.getWorkoutsByUser(currentUser);

        model.addAttribute("workouts", workouts);
        model.addAttribute("user", currentUser);

        return "workout/history";
    }


}
