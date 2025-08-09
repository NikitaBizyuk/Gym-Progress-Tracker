package com.gymtracker.gym_progress_tracker.repository;

import com.gymtracker.gym_progress_tracker.entity.User;
import com.gymtracker.gym_progress_tracker.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findByUserOrderByWorkoutDateDesc(User user);

    long countByUser(User user);

    long countByUserAndWorkoutDateAfter(User user, LocalDateTime date);

    List<Workout> findTop5ByUserOrderByWorkoutDateDesc(User user);
}