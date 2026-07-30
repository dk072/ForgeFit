package com.forgefit.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.forgefit.app.data.model.Exercise
import com.forgefit.app.data.model.Workout
import com.forgefit.app.data.seed.ExerciseSeedData
import com.forgefit.app.ui.navigation.BottomNavBar
import com.forgefit.app.ui.navigation.Screen
import com.forgefit.app.ui.screens.active.ActiveWorkoutScreen
import com.forgefit.app.ui.screens.exercises.ExerciseDetailScreen
import com.forgefit.app.ui.screens.exercises.ExerciseListScreen
import com.forgefit.app.ui.screens.exercises.MuscleMapScreen
import com.forgefit.app.ui.screens.home.HomeScreen
import com.forgefit.app.ui.screens.onboarding.OnboardingScreen
import com.forgefit.app.ui.screens.profile.EquipmentSettingsScreen
import com.forgefit.app.ui.screens.profile.ProfileScreen
import com.forgefit.app.ui.screens.progress.ProgressScreen
import com.forgefit.app.ui.screens.progress.WorkoutHistoryScreen
import com.forgefit.app.ui.screens.workouts.CustomWorkoutBuilderScreen
import com.forgefit.app.ui.screens.workouts.SmartGeneratorScreen
import com.forgefit.app.ui.screens.workouts.WorkoutsScreen
import com.forgefit.app.ui.theme.ForgeFitTheme
import com.forgefit.app.ui.viewmodel.ActiveWorkoutViewModel
import com.forgefit.app.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as ForgeFitApp
        val repository = app.repository

        val mainViewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repository) as T
            }
        }

        val activeViewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ActiveWorkoutViewModel(repository) as T
            }
        }

        setContent {
            ForgeFitTheme {
                val mainViewModel: MainViewModel = viewModel(factory = mainViewModelFactory)
                val activeViewModel: ActiveWorkoutViewModel = viewModel(factory = activeViewModelFactory)

                val userProfile by mainViewModel.userProfile.collectAsState()
                val exercises by mainViewModel.exercises.collectAsState()
                val workouts by mainViewModel.workouts.collectAsState()
                val sessions by mainViewModel.workoutSessions.collectAsState()
                val personalRecords by mainViewModel.personalRecords.collectAsState()

                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val showBottomBar = currentRoute in listOf(
                    Screen.Home.route,
                    Screen.Workouts.route,
                    Screen.Exercises.route,
                    Screen.Progress.route,
                    Screen.Profile.route
                )

                val startDestination = if (userProfile.isOnboarded) Screen.Home.route else Screen.Onboarding.route

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavBar(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = startDestination
                        ) {
                            composable(Screen.Onboarding.route) {
                                OnboardingScreen(
                                    onOnboardingComplete = { updatedProfile ->
                                        mainViewModel.completeOnboarding(updatedProfile)
                                        navController.navigate(Screen.Home.route) {
                                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                                        }
                                    }
                                )
                            }

                            composable(Screen.Home.route) {
                                HomeScreen(
                                    userProfile = userProfile,
                                    todaysWorkout = workouts.firstOrNull(),
                                    recentSessions = sessions,
                                    onStartWorkout = { workout ->
                                        activeViewModel.startWorkout(workout, userProfile)
                                        navController.navigate(Screen.ActiveWorkout.createRoute(workout.id))
                                    },
                                    onNavigateToWorkouts = { navController.navigate(Screen.Workouts.route) },
                                    onNavigateToProgress = { navController.navigate(Screen.Progress.route) }
                                )
                            }

                            composable(Screen.Workouts.route) {
                                WorkoutsScreen(
                                    workouts = workouts,
                                    onSelectWorkout = { workout ->
                                        activeViewModel.startWorkout(workout, userProfile)
                                        navController.navigate(Screen.ActiveWorkout.createRoute(workout.id))
                                    },
                                    onOpenGenerator = { navController.navigate(Screen.SmartGenerator.route) },
                                    onOpenCustomBuilder = { navController.navigate(Screen.CustomBuilder.route) }
                                )
                            }

                            composable(Screen.SmartGenerator.route) {
                                SmartGeneratorScreen(
                                    userProfile = userProfile,
                                    onWorkoutGenerated = { generatedWorkout ->
                                        activeViewModel.startWorkout(generatedWorkout, userProfile)
                                        navController.navigate(Screen.ActiveWorkout.createRoute(generatedWorkout.id)) {
                                            popUpTo(Screen.Workouts.route)
                                        }
                                    },
                                    onBack = { navController.popBackStack() }
                                )
                            }

                            composable(Screen.CustomBuilder.route) {
                                CustomWorkoutBuilderScreen(
                                    onSaveWorkout = { custom ->
                                        // Custom workout saved
                                        navController.popBackStack()
                                    },
                                    onBack = { navController.popBackStack() }
                                )
                            }

                            composable(Screen.Exercises.route) {
                                ExerciseListScreen(
                                    exercises = exercises,
                                    onExerciseClick = { exercise ->
                                        navController.navigate(Screen.ExerciseDetail.createRoute(exercise.id))
                                    },
                                    onFavoriteToggle = { id, isFav ->
                                        mainViewModel.toggleFavorite(id, isFav)
                                    },
                                    onOpenMuscleMap = { navController.navigate(Screen.MuscleMap.route) }
                                )
                            }

                            composable(Screen.MuscleMap.route) {
                                MuscleMapScreen(
                                    exercises = exercises,
                                    onExerciseClick = { exercise ->
                                        navController.navigate(Screen.ExerciseDetail.createRoute(exercise.id))
                                    },
                                    onFavoriteToggle = { id, isFav ->
                                        mainViewModel.toggleFavorite(id, isFav)
                                    },
                                    onBack = { navController.popBackStack() }
                                )
                            }

                            composable(Screen.ExerciseDetail.route) { backStackEntry ->
                                val exerciseId = backStackEntry.arguments?.getString("exerciseId")
                                val exercise = exercises.find { it.id == exerciseId } ?: ExerciseSeedData.ALL_EXERCISES.first()
                                ExerciseDetailScreen(
                                    exercise = exercise,
                                    onBack = { navController.popBackStack() }
                                )
                            }

                            composable(Screen.ActiveWorkout.route) {
                                ActiveWorkoutScreen(
                                    viewModel = activeViewModel,
                                    userProfile = userProfile,
                                    onWorkoutFinished = {
                                        navController.navigate(Screen.Progress.route) {
                                            popUpTo(Screen.Home.route)
                                        }
                                    }
                                )
                            }

                            composable(Screen.Progress.route) {
                                ProgressScreen(
                                    sessions = sessions,
                                    personalRecords = personalRecords,
                                    onNavigateToHistory = { navController.navigate(Screen.WorkoutHistory.route) }
                                )
                            }

                            composable(Screen.WorkoutHistory.route) {
                                WorkoutHistoryScreen(
                                    sessions = sessions,
                                    onBack = { navController.popBackStack() }
                                )
                            }

                            composable(Screen.Profile.route) {
                                ProfileScreen(
                                    userProfile = userProfile,
                                    onUpdateProfile = { updated -> mainViewModel.updateProfile(updated) },
                                    onOpenEquipmentSettings = { navController.navigate(Screen.EquipmentSettings.route) }
                                )
                            }

                            composable(Screen.EquipmentSettings.route) {
                                EquipmentSettingsScreen(
                                    userProfile = userProfile,
                                    onUpdateProfile = { updated -> mainViewModel.updateProfile(updated) },
                                    onBack = { navController.popBackStack() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
