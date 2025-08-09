package com.gymtracker.gym_progress_tracker.repository;

import com.gymtracker.gym_progress_tracker.entity.Workout;
import com.gymtracker.gym_progress_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    // Find all workouts for a specific user
    List<Workout> findByUser(User user);

    // Find workouts by user, ordered by date (newest first)
    List<Workout> findByUserOrderByWorkoutDateDesc(User user);

    // Find workouts within a date range
    List<Workout> findByUserAndWorkoutDateBetween(User user, LocalDateTime start, LocalDateTime end);

    // Count total workouts for a user
    long countByUser(User user);

    // Custom query - find recent workouts (last 30 days)
    @Query("SELECT w FROM Workout w WHERE w.user = ?1 AND w.workoutDate >= ?2 ORDER BY w.workoutDate DESC")
    List<Workout> findRecentWorkouts(User user, LocalDateTime thirtyDaysAgo);
}