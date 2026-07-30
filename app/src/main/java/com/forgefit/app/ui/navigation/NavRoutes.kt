package com.forgefit.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Onboarding : Screen("onboarding", "Onboarding")
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Workouts : Screen("workouts", "Workouts", Icons.Default.FitnessCenter)
    object Exercises : Screen("exercises", "Exercises", Icons.Default.FormatListNumbered)
    object Progress : Screen("progress", "Progress", Icons.Default.BarChart)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)

    object MuscleMap : Screen("muscle_map", "Muscle Map")
    object SmartGenerator : Screen("smart_generator", "Workout Generator")
    object CustomBuilder : Screen("custom_builder", "Custom Builder")
    object ExerciseDetail : Screen("exercise_detail/{exerciseId}", "Exercise Details") {
        fun createRoute(exerciseId: String) = "exercise_detail/$exerciseId"
    }
    object ActiveWorkout : Screen("active_workout/{workoutId}", "Active Workout") {
        fun createRoute(workoutId: String) = "active_workout/$workoutId"
    }
    object WorkoutHistory : Screen("workout_history", "Workout History")
    object EquipmentSettings : Screen("equipment_settings", "Equipment Settings")
}

val BOTTOM_NAV_ITEMS = listOf(
    Screen.Home,
    Screen.Workouts,
    Screen.Exercises,
    Screen.Progress,
    Screen.Profile
)
