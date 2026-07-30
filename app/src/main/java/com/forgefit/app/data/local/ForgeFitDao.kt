package com.forgefit.app.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ForgeFitDao {

    @Query("SELECT * FROM exercise_favorites")
    fun getAllFavorites(): Flow<List<FavoriteExerciseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteExerciseEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteExerciseEntity)

    @Query("SELECT * FROM custom_workouts ORDER BY createdAtMillis DESC")
    fun getCustomWorkouts(): Flow<List<CustomWorkoutEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomWorkout(workout: CustomWorkoutEntity)

    @Query("DELETE FROM custom_workouts WHERE id = :id")
    suspend fun deleteCustomWorkout(id: String)

    @Query("SELECT * FROM workout_sessions ORDER BY timestampMillis DESC")
    fun getWorkoutSessions(): Flow<List<WorkoutSessionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutSession(session: WorkoutSessionEntity)

    @Query("SELECT * FROM personal_records")
    fun getPersonalRecords(): Flow<List<PersonalRecordEntity>>

    @Query("SELECT * FROM personal_records WHERE exerciseId = :exerciseId")
    suspend fun getPersonalRecordForExercise(exerciseId: String): PersonalRecordEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonalRecord(record: PersonalRecordEntity)
}
