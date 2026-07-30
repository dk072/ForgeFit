package com.forgefit.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_favorites")
data class FavoriteExerciseEntity(
    @PrimaryKey val exerciseId: String
)

@Entity(tableName = "custom_workouts")
data class CustomWorkoutEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val difficulty: String,
    val durationMinutes: Int,
    val targetMusclesJson: String,
    val exerciseIdsJson: String,
    val createdAtMillis: Long
)

@Entity(tableName = "workout_sessions")
data class WorkoutSessionEntity(
    @PrimaryKey val sessionId: String,
    val workoutId: String,
    val workoutName: String,
    val timestampMillis: Long,
    val durationSeconds: Int,
    val totalVolumeKg: Float,
    val totalSets: Int,
    val totalReps: Int,
    val sessionDetailsJson: String
)

@Entity(tableName = "personal_records")
data class PersonalRecordEntity(
    @PrimaryKey val exerciseId: String,
    val exerciseName: String,
    val maxWeightKg: Float,
    val maxReps: Int,
    val maxVolumeSingleSetKg: Float,
    val dateAchievedMillis: Long
)
