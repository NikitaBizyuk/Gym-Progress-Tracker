package com.gymtracker.gym_progress_tracker.repository;

import com.gymtracker.gym_progress_tracker.entity.WorkoutExercise;
import com.gymtracker.gym_progress_tracker.entity.Workout;
import com.gymtracker.gym_progress_tracker.entity.Exercise;
import com.gymtracker.gym_progress_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {

    // Find all exercises in a specific workout
    List<WorkoutExercise> findByWorkout(Workout workout);

    // Find all instances where a user did a specific exercise
    List<WorkoutExercise> findByWorkoutUserAndExercise(User user, Exercise exercise);

    // Find user's personal record (max weight) for an exercise
    @Query("SELECT MAX(we.weight) FROM WorkoutExercise we WHERE we.workout.user = ?1 AND we.exercise = ?2")
    BigDecimal findMaxWeightForUserAndExercise(User user, Exercise exercise);

    // Find workout exercises ordered by weight (for progress tracking)
    List<WorkoutExercise> findByWorkoutUserAndExerciseOrderByWorkoutWorkoutDateDesc(User user, Exercise exercise);

    // Count how many times a user has done an exercise
    long countByWorkoutUserAndExercise(User user, Exercise exercise);
}