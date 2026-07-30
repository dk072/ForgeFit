package com.forgefit.app.data.repository

import com.forgefit.app.data.model.*
import com.forgefit.app.data.seed.ExerciseSeedData

object OverloadEngine {

    data class OverloadAdvice(
        val recommendedReps: Int,
        val recommendedSets: Int,
        val recommendedWeightKg: Float,
        val tipMessage: String,
        val alternativeExercise: Exercise? = null
    )

    fun getOverloadAdvice(
        exercise: Exercise,
        previousSessions: List<WorkoutSession>,
        userProfile: UserProfile
    ): OverloadAdvice {
        val baseWeight = userProfile.leftDumbbellWeightKg

        // Find last performance for this exercise
        var maxRepsCompleted = 0
        var maxWeightUsed = baseWeight
        var setsCount = exercise.defaultSets

        for (session in previousSessions) {
            if (session.completedExercisesJson.contains(exercise.id)) {
                // Dummy/parsed max check or simple progression heuristic
                maxRepsCompleted = (exercise.minReps + 2).coerceAtMost(exercise.maxReps)
                break
            }
        }

        return if (maxRepsCompleted >= exercise.maxReps) {
            // User reached top rep range! Recommend advanced overload strategy
            val nextVariation = findHarderAlternative(exercise)
            if (nextVariation != null) {
                OverloadAdvice(
                    recommendedReps = exercise.minReps,
                    recommendedSets = setsCount,
                    recommendedWeightKg = maxWeightUsed,
                    tipMessage = "🔥 Top reps reached! Overload by switching to harder variation: ${nextVariation.name}.",
                    alternativeExercise = nextVariation
                )
            } else {
                OverloadAdvice(
                    recommendedReps = exercise.maxReps,
                    recommendedSets = (setsCount + 1).coerceAtMost(5),
                    recommendedWeightKg = maxWeightUsed,
                    tipMessage = "⚡ Max reps hit! Overload with 3-sec slow eccentric tempo & pause reps.",
                    alternativeExercise = null
                )
            }
        } else if (maxRepsCompleted > 0) {
            OverloadAdvice(
                recommendedReps = (maxRepsCompleted + 1).coerceAtMost(exercise.maxReps),
                recommendedSets = setsCount,
                recommendedWeightKg = maxWeightUsed,
                tipMessage = "💪 Progressive overload: Aim for ${maxRepsCompleted + 1} reps per set today!",
                alternativeExercise = null
            )
        } else {
            OverloadAdvice(
                recommendedReps = exercise.minReps,
                recommendedSets = exercise.defaultSets,
                recommendedWeightKg = maxWeightUsed,
                tipMessage = "🎯 Target crisp technique with ${exercise.minReps}–${exercise.maxReps} controlled reps.",
                alternativeExercise = null
            )
        }
    }

    private fun findHarderAlternative(exercise: Exercise): Exercise? {
        val Map = mapOf(
            "push_up" to "decline_push_up",
            "db_floor_press" to "single_arm_floor_press",
            "goblet_squat" to "bulgarian_split_squat",
            "db_curl" to "zottman_curl"
        )
        val targetId = Map[exercise.id] ?: return null
        return ExerciseSeedData.ALL_EXERCISES.find { it.id == targetId }
    }

    fun generateSmartWorkout(
        targetMuscles: List<MuscleGroup>,
        difficulty: Difficulty,
        durationMinutes: Int,
        userProfile: UserProfile
    ): Workout {
        val eligibleExercises = ExerciseSeedData.ALL_EXERCISES.filter { ex ->
            userProfile.enabledEquipment.contains(ex.equipmentRequired) &&
                    (targetMuscles.isEmpty() || targetMuscles.contains(ex.primaryMuscle) || ex.secondaryMuscles.any { targetMuscles.contains(it) })
        }

        val targetCount = when {
            durationMinutes <= 25 -> 4
            durationMinutes <= 40 -> 5
            durationMinutes <= 55 -> 6
            else -> 7
        }

        val selected = eligibleExercises.take(targetCount).ifEmpty {
            ExerciseSeedData.ALL_EXERCISES.take(targetCount)
        }

        val name = if (targetMuscles.isNotEmpty()) {
            "${targetMuscles.joinToString(" & ") { it.displayName }} Workout"
        } else {
            "Custom Dumbbell Express"
        }

        return Workout(
            id = "smart_gen_${System.currentTimeMillis()}",
            name = name,
            description = "Smart generated 2-Dumbbell & Bodyweight routine tailored for $durationMinutes minutes.",
            difficulty = difficulty,
            estimatedDurationMinutes = durationMinutes,
            targetMuscles = if (targetMuscles.isEmpty()) selected.map { it.primaryMuscle }.distinct() else targetMuscles,
            exerciseIds = selected.map { it.id },
            isCustom = true
        )
    }
}
