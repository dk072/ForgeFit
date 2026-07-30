package com.forgefit.app.data.model

data class Exercise(
    val id: String,
    val name: String,
    val description: String,
    val primaryMuscle: MuscleGroup,
    val secondaryMuscles: List<MuscleGroup>,
    val equipmentRequired: Equipment,
    val difficulty: Difficulty,
    val movementType: MovementType,
    val bodyPosition: BodyPosition,
    val defaultSets: Int,
    val minReps: Int,
    val maxReps: Int,
    val restTimeSeconds: Int,
    val instructions: List<String>,
    val formTips: List<String>,
    val commonMistakes: List<String>,
    val safetyNotes: String,
    val animationKey: String, // Animation identifier for procedural renderer
    val isFavorite: Boolean = false
)

data class Workout(
    val id: String,
    val name: String,
    val description: String,
    val difficulty: Difficulty,
    val estimatedDurationMinutes: Int,
    val targetMuscles: List<MuscleGroup>,
    val exerciseIds: List<String>,
    val isCustom: Boolean = false
)

data class UserProfile(
    val experienceLevel: ExperienceLevel = ExperienceLevel.BEGINNER,
    val goal: Goal = Goal.BUILD_MUSCLE,
    val workoutDaysPerWeek: Int = 3,
    val targetDurationMinutes: Int = 45,
    val dumbbellType: DumbbellType = DumbbellType.FIXED,
    val leftDumbbellWeightKg: Float = 5.0f,
    val rightDumbbellWeightKg: Float = 5.0f,
    val weightUnit: WeightUnit = WeightUnit.KG,
    val enabledEquipment: List<Equipment> = listOf(
        Equipment.DUMBBELL_2,
        Equipment.DUMBBELL_1,
        Equipment.BODYWEIGHT
    ),
    val isOnboarded: Boolean = false,
    val defaultRestTimeSeconds: Int = 60,
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true
)

data class WorkoutSet(
    val setNumber: Int,
    val targetReps: Int,
    val completedReps: Int,
    val weightKg: Float,
    val isCompleted: Boolean = false
)

data class WorkoutSession(
    val sessionId: String,
    val workoutId: String,
    val workoutName: String,
    val timestampMillis: Long,
    val durationSeconds: Int,
    val totalVolumeKg: Float,
    val totalSets: Int,
    val totalReps: Int,
    val completedExercisesJson: String // Serialized set data
)

data class PersonalRecord(
    val exerciseId: String,
    val exerciseName: String,
    val maxWeightKg: Float,
    val maxReps: Int,
    val maxVolumeSingleSetKg: Float,
    val dateAchievedMillis: Long
)
