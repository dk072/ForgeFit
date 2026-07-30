package com.forgefit.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forgefit.app.data.model.*
import com.forgefit.app.data.repository.ForgeFitRepository
import com.forgefit.app.data.repository.OverloadEngine
import com.forgefit.app.data.seed.ExerciseSeedData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ActiveWorkoutUiState(
    val workout: Workout? = null,
    val exercises: List<Exercise> = emptyList(),
    val currentExerciseIndex: Int = 0,
    val currentSetIndex: Int = 0,
    val setsPerExercise: Map<String, List<WorkoutSet>> = emptyMap(),
    val isRestActive: Boolean = false,
    val restDurationSeconds: Int = 60,
    val isFinished: Boolean = false,
    val startTimeMillis: Long = System.currentTimeMillis(),
    val overloadAdvice: OverloadEngine.OverloadAdvice? = null,
    val latestNewPR: PersonalRecord? = null
)

class ActiveWorkoutViewModel(private val repository: ForgeFitRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ActiveWorkoutUiState())
    val uiState: StateFlow<ActiveWorkoutUiState> = _uiState.asStateFlow()

    fun startWorkout(workout: Workout, userProfile: UserProfile) {
        val exerciseList = workout.exerciseIds.mapNotNull { id ->
            ExerciseSeedData.ALL_EXERCISES.find { it.id == id }
        }

        val initialSetsMap = exerciseList.associate { ex ->
            val sets = (1..ex.defaultSets).map { setNum ->
                WorkoutSet(
                    setNumber = setNum,
                    targetReps = ex.minReps,
                    completedReps = ex.minReps,
                    weightKg = userProfile.leftDumbbellWeightKg,
                    isCompleted = false
                )
            }
            ex.id to sets
        }

        val initialAdvice = exerciseList.firstOrNull()?.let {
            OverloadEngine.getOverloadAdvice(it, emptyList(), userProfile)
        }

        _uiState.value = ActiveWorkoutUiState(
            workout = workout,
            exercises = exerciseList,
            currentExerciseIndex = 0,
            currentSetIndex = 0,
            setsPerExercise = initialSetsMap,
            isRestActive = false,
            restDurationSeconds = userProfile.defaultRestTimeSeconds,
            startTimeMillis = System.currentTimeMillis(),
            overloadAdvice = initialAdvice
        )
    }

    fun completeSet(reps: Int, weightKg: Float, userProfile: UserProfile) {
        val state = _uiState.value
        val currentEx = state.exercises.getOrNull(state.currentExerciseIndex) ?: return
        val currentSets = state.setsPerExercise[currentEx.id]?.toMutableList() ?: return

        if (state.currentSetIndex < currentSets.size) {
            currentSets[state.currentSetIndex] = WorkoutSet(
                setNumber = state.currentSetIndex + 1,
                targetReps = currentSets[state.currentSetIndex].targetReps,
                completedReps = reps,
                weightKg = weightKg,
                isCompleted = true
            )
        }

        val updatedMap = state.setsPerExercise.toMutableMap()
        updatedMap[currentEx.id] = currentSets

        // Check PR
        viewModelScope.launch {
            val isNewPR = repository.updatePersonalRecordIfHigher(currentEx.id, currentEx.name, weightKg, reps)
            if (isNewPR) {
                _uiState.value = _uiState.value.copy(
                    latestNewPR = PersonalRecord(
                        exerciseId = currentEx.id,
                        exerciseName = currentEx.name,
                        maxWeightKg = weightKg,
                        maxReps = reps,
                        maxVolumeSingleSetKg = weightKg * reps,
                        dateAchievedMillis = System.currentTimeMillis()
                    )
                )
            }
        }

        // Trigger rest timer or next set
        if (state.currentSetIndex + 1 < currentSets.size) {
            _uiState.value = state.copy(
                setsPerExercise = updatedMap,
                currentSetIndex = state.currentSetIndex + 1,
                isRestActive = true,
                restDurationSeconds = currentEx.restTimeSeconds
            )
        } else {
            // Move to next exercise or finish
            if (state.currentExerciseIndex + 1 < state.exercises.size) {
                val nextIndex = state.currentExerciseIndex + 1
                val nextEx = state.exercises[nextIndex]
                val nextAdvice = OverloadEngine.getOverloadAdvice(nextEx, emptyList(), userProfile)
                _uiState.value = state.copy(
                    setsPerExercise = updatedMap,
                    currentExerciseIndex = nextIndex,
                    currentSetIndex = 0,
                    isRestActive = true,
                    restDurationSeconds = currentEx.restTimeSeconds,
                    overloadAdvice = nextAdvice
                )
            } else {
                // Workout Finished!
                finishWorkout(updatedMap)
            }
        }
    }

    fun skipRest() {
        _uiState.value = _uiState.value.copy(isRestActive = false)
    }

    fun dismissPRDialog() {
        _uiState.value = _uiState.value.copy(latestNewPR = null)
    }

    fun replaceCurrentExercise(newExercise: Exercise, userProfile: UserProfile) {
        val state = _uiState.value
        if (state.exercises.isEmpty()) return

        val updatedExercises = state.exercises.toMutableList()
        updatedExercises[state.currentExerciseIndex] = newExercise

        val initialSets = (1..newExercise.defaultSets).map { setNum ->
            WorkoutSet(
                setNumber = setNum,
                targetReps = newExercise.minReps,
                completedReps = newExercise.minReps,
                weightKg = userProfile.leftDumbbellWeightKg,
                isCompleted = false
            )
        }
        val updatedMap = state.setsPerExercise.toMutableMap()
        updatedMap[newExercise.id] = initialSets

        val newAdvice = OverloadEngine.getOverloadAdvice(newExercise, emptyList(), userProfile)

        _uiState.value = state.copy(
            exercises = updatedExercises,
            currentSetIndex = 0,
            setsPerExercise = updatedMap,
            overloadAdvice = newAdvice
        )
    }

    fun finishWorkout(setsMap: Map<String, List<WorkoutSet>> = _uiState.value.setsPerExercise) {
        val state = _uiState.value
        val workout = state.workout ?: return

        var totalVol = 0f
        var totalSets = 0
        var totalReps = 0

        setsMap.values.flatten().forEach { set ->
            if (set.isCompleted) {
                totalVol += set.weightKg * set.completedReps
                totalSets += 1
                totalReps += set.completedReps
            }
        }

        val durationSec = ((System.currentTimeMillis() - state.startTimeMillis) / 1000).toInt().coerceAtLeast(60)

        val session = WorkoutSession(
            sessionId = "session_${System.currentTimeMillis()}",
            workoutId = workout.id,
            workoutName = workout.name,
            timestampMillis = System.currentTimeMillis(),
            durationSeconds = durationSec,
            totalVolumeKg = totalVol,
            totalSets = totalSets,
            totalReps = totalReps,
            completedExercisesJson = workout.exerciseIds.joinToString(",")
        )

        viewModelScope.launch {
            repository.saveWorkoutSession(session)
        }

        _uiState.value = state.copy(isFinished = true, isRestActive = false)
    }
}
