package com.forgefit.app.data.repository

import com.forgefit.app.data.local.*
import com.forgefit.app.data.model.*
import com.forgefit.app.data.seed.ExerciseSeedData
import com.forgefit.app.data.seed.WorkoutSeedData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class ForgeFitRepository(
    private val dao: ForgeFitDao,
    private val userPrefs: UserPreferencesDataStore
) {

    val userProfileFlow: Flow<UserProfile> = userPrefs.userProfileFlow

    suspend fun updateUserProfile(profile: UserProfile) {
        userPrefs.updateUserProfile(profile)
    }

    suspend fun setOnboarded(onboarded: Boolean) {
        userPrefs.setOnboarded(onboarded)
    }

    val favoritesFlow: Flow<List<String>> = dao.getAllFavorites().map { list ->
        list.map { it.exerciseId }
    }

    val exercisesFlow: Flow<List<Exercise>> = combine(favoritesFlow, userProfileFlow) { favorites, profile ->
        ExerciseSeedData.ALL_EXERCISES.map { exercise ->
            exercise.copy(
                isFavorite = favorites.contains(exercise.id)
            )
        }.filter { exercise ->
            profile.enabledEquipment.contains(exercise.equipmentRequired)
        }
    }

    suspend fun toggleFavorite(exerciseId: String, currentFavorite: Boolean) {
        if (currentFavorite) {
            dao.deleteFavorite(FavoriteExerciseEntity(exerciseId))
        } else {
            dao.insertFavorite(FavoriteExerciseEntity(exerciseId))
        }
    }

    val customWorkoutsFlow: Flow<List<Workout>> = dao.getCustomWorkouts().map { list ->
        list.map { entity ->
            val targetMuscles = entity.targetMusclesJson.split(",").mapNotNull {
                try { MuscleGroup.valueOf(it.trim()) } catch (e: Exception) { null }
            }
            val exerciseIds = entity.exerciseIdsJson.split(",").map { it.trim() }.filter { it.isNotEmpty() }
            Workout(
                id = entity.id,
                name = entity.name,
                description = entity.description,
                difficulty = try { Difficulty.valueOf(entity.difficulty) } catch (e: Exception) { Difficulty.BEGINNER },
                estimatedDurationMinutes = entity.durationMinutes,
                targetMuscles = targetMuscles,
                exerciseIds = exerciseIds,
                isCustom = true
            )
        }
    }

    val allWorkoutsFlow: Flow<List<Workout>> = customWorkoutsFlow.map { custom ->
        WorkoutSeedData.PREBUILT_WORKOUTS + custom
    }

    suspend fun saveCustomWorkout(workout: Workout) {
        val entity = CustomWorkoutEntity(
            id = workout.id,
            name = workout.name,
            description = workout.description,
            difficulty = workout.difficulty.name,
            durationMinutes = workout.estimatedDurationMinutes,
            targetMusclesJson = workout.targetMuscles.joinToString(",") { it.name },
            exerciseIdsJson = workout.exerciseIds.joinToString(","),
            createdAtMillis = System.currentTimeMillis()
        )
        dao.insertCustomWorkout(entity)
    }

    suspend fun deleteCustomWorkout(id: String) {
        dao.deleteCustomWorkout(id)
    }

    val workoutSessionsFlow: Flow<List<WorkoutSession>> = dao.getWorkoutSessions().map { list ->
        list.map { entity ->
            WorkoutSession(
                sessionId = entity.sessionId,
                workoutId = entity.workoutId,
                workoutName = entity.workoutName,
                timestampMillis = entity.timestampMillis,
                durationSeconds = entity.durationSeconds,
                totalVolumeKg = entity.totalVolumeKg,
                totalSets = entity.totalSets,
                totalReps = entity.totalReps,
                completedExercisesJson = entity.sessionDetailsJson
            )
        }
    }

    suspend fun saveWorkoutSession(session: WorkoutSession) {
        val entity = WorkoutSessionEntity(
            sessionId = session.sessionId,
            workoutId = session.workoutId,
            workoutName = session.workoutName,
            timestampMillis = session.timestampMillis,
            durationSeconds = session.durationSeconds,
            totalVolumeKg = session.totalVolumeKg,
            totalSets = session.totalSets,
            totalReps = session.totalReps,
            sessionDetailsJson = session.completedExercisesJson
        )
        dao.insertWorkoutSession(entity)
    }

    val personalRecordsFlow: Flow<List<PersonalRecord>> = dao.getPersonalRecords().map { list ->
        list.map { entity ->
            PersonalRecord(
                exerciseId = entity.exerciseId,
                exerciseName = entity.exerciseName,
                maxWeightKg = entity.maxWeightKg,
                maxReps = entity.maxReps,
                maxVolumeSingleSetKg = entity.maxVolumeSingleSetKg,
                dateAchievedMillis = entity.dateAchievedMillis
            )
        }
    }

    suspend fun updatePersonalRecordIfHigher(
        exerciseId: String,
        exerciseName: String,
        weightKg: Float,
        reps: Int
    ): Boolean {
        val existing = dao.getPersonalRecordForExercise(exerciseId)
        val singleSetVolume = weightKg * reps

        var isNewRecord = false
        if (existing == null) {
            isNewRecord = true
            dao.insertPersonalRecord(
                PersonalRecordEntity(
                    exerciseId = exerciseId,
                    exerciseName = exerciseName,
                    maxWeightKg = weightKg,
                    maxReps = reps,
                    maxVolumeSingleSetKg = singleSetVolume,
                    dateAchievedMillis = System.currentTimeMillis()
                )
            )
        } else {
            val newMaxWeight = maxOf(existing.maxWeightKg, weightKg)
            val newMaxReps = maxOf(existing.maxReps, reps)
            val newMaxVol = maxOf(existing.maxVolumeSingleSetKg, singleSetVolume)

            if (weightKg > existing.maxWeightKg || reps > existing.maxReps || singleSetVolume > existing.maxVolumeSingleSetKg) {
                isNewRecord = true
                dao.insertPersonalRecord(
                    PersonalRecordEntity(
                        exerciseId = exerciseId,
                        exerciseName = exerciseName,
                        maxWeightKg = newMaxWeight,
                        maxReps = newMaxReps,
                        maxVolumeSingleSetKg = newMaxVol,
                        dateAchievedMillis = System.currentTimeMillis()
                    )
                )
            }
        }
        return isNewRecord
    }
}
