package com.gymtracker.gym_progress_tracker.service;

import com.gymtracker.gym_progress_tracker.entity.Exercise;
import com.gymtracker.gym_progress_tracker.entity.User;
import com.gymtracker.gym_progress_tracker.entity.Workout;
import com.gymtracker.gym_progress_tracker.entity.WorkoutExercise;
import com.gymtracker.gym_progress_tracker.repository.ExerciseRepository;
import com.gymtracker.gym_progress_tracker.repository.WorkoutExerciseRepository;
import com.gymtracker.gym_progress_tracker.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired WorkoutExerciseRepository workoutExerciseRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Transactional
    public Workout saveWorkout(Workout workout, List<Long> exerciseIds, List<Integer> sets,
                               List<Integer> reps, List<Double> weights, List<String> exerciseNotes){

        // save workout first
        Workout savedWorkout = workoutRepository.save(workout);

        // Add exercises to the workout
        for(int i = 0; i < exerciseIds.size(); i++) {
            if(exerciseIds.get(i) != null && sets.get(i) != null &&
            reps.get(i) != null && weights.get(i) != null){

                Optional<Exercise> exerciseOpt = exerciseRepository.findById(exerciseIds.get(i));
                if(exerciseOpt.isPresent()){
                    WorkoutExercise workoutExercise = new WorkoutExercise();
                    workoutExercise.setWorkout(savedWorkout);
                    workoutExercise.setExercise(exerciseOpt.get());
                    workoutExercise.setSets(sets.get(i));
                    workoutExercise.setReps(reps.get(i));
                    workoutExercise.setWeight(BigDecimal.valueOf(weights.get(i)));

                    // Add notes if provided
                    if(exerciseNotes != null && i < exerciseNotes.size() &&
                    exerciseNotes.get(i) != null && !exerciseNotes.get(i).trim().isEmpty()){
                        workoutExercise.setNotes(exerciseNotes.get(i));
                    }

                    workoutExerciseRepository.save(workoutExercise);
                }
            }

        }
        return savedWorkout;
    }

    public List<Workout> getWorkoutsByUser(User user){
        return workoutRepository.findByUserOrderByWorkoutDateDesc(user);
    }
    public Workout getWorkoutById(Long id) {
        return workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found with id: " + id));
    }

    public long getTotalWorkoutsForUser(User user) {
        return workoutRepository.countByUser(user);
    }

    public long getWorkoutsThisWeekForUser(User user) {
        LocalDateTime startOfWeek = LocalDateTime.now().minusDays(7);
        return workoutRepository.countByUserAndWorkoutDateAfter(user, startOfWeek);
    }

    public List<Workout> getRecentWorkouts(User user, int limit) {
        return workoutRepository.findTop5ByUserOrderByWorkoutDateDesc(user);
    }

    public void deleteWorkout(Long workoutId, User user) {
        Workout workout = getWorkoutById(workoutId);
        if (workout.getUser().getId().equals(user.getId())) {
            workoutRepository.delete(workout);
        } else {
            throw new RuntimeException("Unauthorized to delete this workout");
        }
    }

}
