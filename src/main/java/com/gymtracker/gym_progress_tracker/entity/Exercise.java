package com.gymtracker.gym_progress_tracker.entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    private MuscleGroup muscleGroup;

    @Enumerated(EnumType.STRING)
    private ExerciseType type;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    private List<WorkoutExercise> workoutExercises;

    //Enums for muscle groups and exercise types
    public enum MuscleGroup{
        CHEST, BACK, SHOULDERS, BICEPS, TRICEPS, LEGS, GLUTES, CORE, CARDIO
    }

    public enum ExerciseType {
        STRENGTH, CARDIO, FLEXIBILITY, SPORTS
    }

    // Constructors
    public Exercise(){

    }
    public Exercise(String name, MuscleGroup muscleGroup, ExerciseType type) {
        this.name = name;
        this.muscleGroup = muscleGroup;
        this.type = type;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public MuscleGroup getMuscleGroup() { return muscleGroup; }
    public void setMuscleGroup(MuscleGroup muscleGroup) { this.muscleGroup = muscleGroup; }

    public ExerciseType getType() { return type; }
    public void setType(ExerciseType type) { this.type = type; }

    public List<WorkoutExercise> getWorkoutExercises() { return workoutExercises; }
    public void setWorkoutExercises(List<WorkoutExercise> workoutExercises) { this.workoutExercises = workoutExercises; }
}


