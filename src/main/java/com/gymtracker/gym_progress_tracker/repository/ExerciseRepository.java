package com.gymtracker.gym_progress_tracker.repository;

import com.gymtracker.gym_progress_tracker.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    // Find exercises by muscle group
    List<Exercise> findByMuscleGroup(Exercise.MuscleGroup muscleGroup);

    // Find exercises by type
    List<Exercise> findByType(Exercise.ExerciseType type);

    // Search exercises by name (case insensitive)
    List<Exercise> findByNameContainingIgnoreCase(String name);

    // Find exercises by muscle group and type
    List<Exercise> findByMuscleGroupAndType(Exercise.MuscleGroup muscleGroup, Exercise.ExerciseType type);
}