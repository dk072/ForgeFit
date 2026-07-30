package com.forgefit.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forgefit.app.data.model.*
import com.forgefit.app.data.repository.ForgeFitRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ForgeFitRepository) : ViewModel() {

    val userProfile: StateFlow<UserProfile> = repository.userProfileFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, UserProfile())

    val exercises: StateFlow<List<Exercise>> = repository.exercisesFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val workouts: StateFlow<List<Workout>> = repository.allWorkoutsFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val workoutSessions: StateFlow<List<WorkoutSession>> = repository.workoutSessionsFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val personalRecords: StateFlow<List<PersonalRecord>> = repository.personalRecordsFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun updateProfile(profile: UserProfile) {
        viewModelScope.launch {
            repository.updateUserProfile(profile)
        }
    }

    fun completeOnboarding(profile: UserProfile) {
        viewModelScope.launch {
            repository.updateUserProfile(profile.copy(isOnboarded = true))
            repository.setOnboarded(true)
        }
    }

    fun toggleFavorite(exerciseId: String, currentFav: Boolean) {
        viewModelScope.launch {
            repository.toggleFavorite(exerciseId, currentFav)
        }
    }
}
