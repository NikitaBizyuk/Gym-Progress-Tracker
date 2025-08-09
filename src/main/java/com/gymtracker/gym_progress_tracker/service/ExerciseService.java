package com.gymtracker.gym_progress_tracker.service;
import com.gymtracker.gym_progress_tracker.entity.Exercise;
import com.gymtracker.gym_progress_tracker.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    // Initialize some default exercises when the app starts
    @PostConstruct
    public void initializeDefaultExercises() {
        if (exerciseRepository.count() == 0) {
            System.out.println("Creating default exercises...");

            // Chest exercises
            createExercise("Bench Press", "Lie on bench and press barbell up",
                    Exercise.MuscleGroup.CHEST, Exercise.ExerciseType.STRENGTH);
            createExercise("Push-ups", "Classic bodyweight chest exercise",
                    Exercise.MuscleGroup.CHEST, Exercise.ExerciseType.STRENGTH);
            createExercise("Incline Dumbbell Press", "Chest exercise on inclined bench",
                    Exercise.MuscleGroup.CHEST, Exercise.ExerciseType.STRENGTH);

            // Back exercises
            createExercise("Pull-ups", "Hang from bar and pull yourself up",
                    Exercise.MuscleGroup.BACK, Exercise.ExerciseType.STRENGTH);
            createExercise("Deadlift", "Lift barbell from ground to hip level",
                    Exercise.MuscleGroup.BACK, Exercise.ExerciseType.STRENGTH);
            createExercise("Bent Over Row", "Row barbell while bent over",
                    Exercise.MuscleGroup.BACK, Exercise.ExerciseType.STRENGTH);

            // Leg exercises
            createExercise("Squats", "Lower body compound movement",
                    Exercise.MuscleGroup.LEGS, Exercise.ExerciseType.STRENGTH);
            createExercise("Lunges", "Single-leg strength exercise",
                    Exercise.MuscleGroup.LEGS, Exercise.ExerciseType.STRENGTH);
            createExercise("Leg Press", "Seated leg pressing machine",
                    Exercise.MuscleGroup.LEGS, Exercise.ExerciseType.STRENGTH);

            // Cardio
            createExercise("Running", "Cardiovascular endurance exercise",
                    Exercise.MuscleGroup.CARDIO, Exercise.ExerciseType.CARDIO);
            createExercise("Cycling", "Stationary or outdoor bike riding",
                    Exercise.MuscleGroup.CARDIO, Exercise.ExerciseType.CARDIO);

            System.out.println("Created " + exerciseRepository.count() + " default exercises!");
        }
    }

    private void createExercise(String name, String description,
                                Exercise.MuscleGroup muscleGroup, Exercise.ExerciseType type) {
        Exercise exercise = new Exercise(name, muscleGroup, type);
        exercise.setDescription(description);
        exerciseRepository.save(exercise);
    }

    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    public List<Exercise> getExercisesByMuscleGroup(Exercise.MuscleGroup muscleGroup) {
        return exerciseRepository.findByMuscleGroup(muscleGroup);
    }

    public Exercise getExerciseById(Long id) {
        return exerciseRepository.findById(id).orElse(null);
    }
}