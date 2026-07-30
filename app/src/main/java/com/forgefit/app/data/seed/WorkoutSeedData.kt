package com.forgefit.app.data.seed

import com.forgefit.app.data.model.*

object WorkoutSeedData {

    val PREBUILT_WORKOUTS = listOf(
        Workout(
            id = "prog_beginner_full_body_a",
            name = "Beginner Full Body A",
            description = "Balanced 3-day split full body workout focusing on foundational dumbbell compound lifts for chest, back, legs, and arms.",
            difficulty = Difficulty.BEGINNER,
            estimatedDurationMinutes = 35,
            targetMuscles = listOf(MuscleGroup.CHEST, MuscleGroup.BACK, MuscleGroup.QUADRICEPS, MuscleGroup.SHOULDERS, MuscleGroup.BICEPS, MuscleGroup.TRICEPS),
            exerciseIds = listOf("goblet_squat", "db_floor_press", "db_bent_over_row", "db_shoulder_press", "db_curl", "plank")
        ),
        Workout(
            id = "prog_beginner_full_body_b",
            name = "Beginner Full Body B",
            description = "Complementary 3-day split workout emphasizing posterior chain, shoulders, and core balance.",
            difficulty = Difficulty.BEGINNER,
            estimatedDurationMinutes = 35,
            targetMuscles = listOf(MuscleGroup.HAMSTRINGS, MuscleGroup.CHEST, MuscleGroup.BACK, MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS, MuscleGroup.ABS),
            exerciseIds = listOf("db_rdl", "push_up", "one_arm_db_row", "lateral_raise", "db_overhead_tricep_ext", "weighted_crunch")
        ),
        Workout(
            id = "prog_upper_power",
            name = "Upper Body Power",
            description = "Upper body push & pull workout designed for muscle hypertrophy using dumbbells.",
            difficulty = Difficulty.INTERMEDIATE,
            estimatedDurationMinutes = 45,
            targetMuscles = listOf(MuscleGroup.CHEST, MuscleGroup.BACK, MuscleGroup.SHOULDERS, MuscleGroup.BICEPS, MuscleGroup.TRICEPS),
            exerciseIds = listOf("db_floor_press", "db_bent_over_row", "arnold_press", "db_squeeze_press", "db_pullover", "hammer_curl", "close_grip_floor_press")
        ),
        Workout(
            id = "prog_lower_power",
            name = "Lower Body & Core",
            description = "Lower body routine hitting quads, hamstrings, glutes, calves, and core using dumbbells.",
            difficulty = Difficulty.INTERMEDIATE,
            estimatedDurationMinutes = 45,
            targetMuscles = listOf(MuscleGroup.QUADRICEPS, MuscleGroup.HAMSTRINGS, MuscleGroup.GLUTES, MuscleGroup.CALVES, MuscleGroup.ABS),
            exerciseIds = listOf("goblet_squat", "db_rdl", "db_lunge", "db_glute_bridge", "db_calf_raise", "db_russian_twist")
        ),
        Workout(
            id = "prog_push_day",
            name = "Push Day (Chest, Shoulders, Triceps)",
            description = "Targeted pushing session to build chest width, rounded shoulder caps, and triceps strength.",
            difficulty = Difficulty.INTERMEDIATE,
            estimatedDurationMinutes = 45,
            targetMuscles = listOf(MuscleGroup.CHEST, MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
            exerciseIds = listOf("db_floor_press", "db_shoulder_press", "db_squeeze_press", "lateral_raise", "db_overhead_tricep_ext", "close_grip_floor_press")
        ),
        Workout(
            id = "prog_pull_day",
            name = "Pull Day (Back, Biceps, Rear Delts)",
            description = "Dedicated pulling workout for thick back development, rear delts, and biceps.",
            difficulty = Difficulty.INTERMEDIATE,
            estimatedDurationMinutes = 45,
            targetMuscles = listOf(MuscleGroup.BACK, MuscleGroup.BICEPS, MuscleGroup.SHOULDERS, MuscleGroup.FOREARMS),
            exerciseIds = listOf("db_bent_over_row", "one_arm_db_row", "rear_delt_fly", "db_curl", "hammer_curl", "farmer_hold")
        ),
        Workout(
            id = "prog_legs_abs",
            name = "Legs & Core Destroyer",
            description = "Comprehensive leg workout focusing on quads, glutes, hamstrings, calves, and abs.",
            difficulty = Difficulty.ADVANCED,
            estimatedDurationMinutes = 50,
            targetMuscles = listOf(MuscleGroup.QUADRICEPS, MuscleGroup.HAMSTRINGS, MuscleGroup.GLUTES, MuscleGroup.CALVES, MuscleGroup.ABS),
            exerciseIds = listOf("bulgarian_split_squat", "goblet_squat", "db_rdl", "db_glute_bridge", "db_calf_raise", "mountain_climbers", "weighted_crunch")
        ),
        Workout(
            id = "prog_strength_hypertrophy",
            name = "Fixed Dumbbell Hypertrophy",
            description = "Specialized volume workout structured for maximum muscle stimulation when limited to fixed weights.",
            difficulty = Difficulty.INTERMEDIATE,
            estimatedDurationMinutes = 40,
            targetMuscles = listOf(MuscleGroup.CHEST, MuscleGroup.BACK, MuscleGroup.SHOULDERS, MuscleGroup.BICEPS, MuscleGroup.TRICEPS),
            exerciseIds = listOf("db_squeeze_press", "one_arm_db_row", "arnold_press", "db_floor_fly", "zottman_curl", "db_tricep_kickback")
        )
    )
}
