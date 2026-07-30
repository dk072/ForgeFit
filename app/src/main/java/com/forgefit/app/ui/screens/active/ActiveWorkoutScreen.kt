package com.forgefit.app.ui.screens.active

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.forgefit.app.data.model.*
import com.forgefit.app.data.seed.ExerciseSeedData
import com.forgefit.app.ui.components.ExerciseAnimationCanvas
import com.forgefit.app.ui.components.MuscleBadge
import com.forgefit.app.ui.components.PRCelebrationDialog
import com.forgefit.app.ui.components.RestTimerOverlay
import com.forgefit.app.ui.theme.*
import com.forgefit.app.ui.viewmodel.ActiveWorkoutViewModel

@Composable
fun ActiveWorkoutScreen(
    viewModel: ActiveWorkoutViewModel,
    userProfile: UserProfile,
    onWorkoutFinished: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val workout = state.workout ?: return

    if (state.isFinished) {
        LaunchedEffect(Unit) {
            onWorkoutFinished()
        }
        return
    }

    val currentEx = state.exercises.getOrNull(state.currentExerciseIndex) ?: return
    val currentSets = state.setsPerExercise[currentEx.id] ?: emptyList()
    val currentSet = currentSets.getOrNull(state.currentSetIndex)

    var inputReps by remember(state.currentExerciseIndex, state.currentSetIndex) {
        mutableStateOf("${currentSet?.completedReps ?: currentEx.minReps}")
    }
    var inputWeight by remember(state.currentExerciseIndex, state.currentSetIndex) {
        mutableStateOf("${currentSet?.weightKg ?: userProfile.leftDumbbellWeightKg}")
    }

    var showReplaceDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = workout.name.uppercase(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonGold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "EXERCISE ${state.currentExerciseIndex + 1} OF ${state.exercises.size}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextPrimary
                    )
                }

                Button(
                    onClick = { viewModel.finishWorkout() },
                    colors = ButtonDefaults.buttonColors(containerColor = SoftRed.copy(alpha = 0.2f)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Stop, contentDescription = "End Workout", tint = SoftRed, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("END WORKOUT", fontSize = 11.sp, color = SoftRed, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // LIVE ANIMATED EXERCISE DEMONSTRATION
            ExerciseAnimationCanvas(
                animationKey = currentEx.animationKey,
                primaryMuscle = currentEx.primaryMuscle,
                heightDp = 200
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Exercise Title & Replace Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = currentEx.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        MuscleBadge(muscle = currentEx.primaryMuscle)
                    }
                }

                TextButton(onClick = { showReplaceDialog = true }) {
                    Icon(Icons.Default.Refresh, contentDescription = null, tint = ElectricBlue, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("REPLACE", color = ElectricBlue, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Progressive Overload Advice Banner
            state.overloadAdvice?.let { advice ->
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    color = SurfaceVariantDark,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = advice.tipMessage,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = NeonGold,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // SET TRACKING CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "SET ${state.currentSetIndex + 1} OF ${currentSets.size}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = NeonGold
                    )
                    Text(
                        text = "Target: ${currentEx.minReps}–${currentEx.maxReps} reps",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedTextField(
                            value = inputWeight,
                            onValueChange = { inputWeight = it },
                            label = { Text("Weight (${userProfile.weightUnit.name})") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = NeonGold,
                                unfocusedBorderColor = CardBorderDark
                            ),
                            modifier = Modifier.weight(1f)
                        )

                        OutlinedTextField(
                            value = inputReps,
                            onValueChange = { inputReps = it },
                            label = { Text("Completed Reps") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = NeonGold,
                                unfocusedBorderColor = CardBorderDark
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // COMPLETE SET CTA
                    Button(
                        onClick = {
                            val reps = inputReps.toIntOrNull() ?: currentEx.minReps
                            val wKg = inputWeight.toFloatOrNull() ?: userProfile.leftDumbbellWeightKg
                            viewModel.completeSet(reps, wKg, userProfile)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonGold),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = BackgroundDark)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "COMPLETE SET",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = BackgroundDark
                        )
                    }
                }
            }
        }

        // REST TIMER OVERLAY
        if (state.isRestActive) {
            RestTimerOverlay(
                totalSeconds = state.restDurationSeconds,
                onRestFinished = { viewModel.skipRest() },
                soundEnabled = userProfile.soundEnabled,
                vibrationEnabled = userProfile.vibrationEnabled
            )
        }

        // PR CELEBRATION OVERLAY
        state.latestNewPR?.let { pr ->
            PRCelebrationDialog(
                record = pr,
                onDismiss = { viewModel.dismissPRDialog() }
            )
        }

        // REPLACE EXERCISE DIALOG
        if (showReplaceDialog) {
            AlertDialog(
                onDismissRequest = { showReplaceDialog = false },
                containerColor = SurfaceDark,
                title = { Text("Replace Exercise", fontWeight = FontWeight.Bold, color = TextPrimary) },
                text = {
                    val availableReplacements = remember(currentEx) {
                        ExerciseSeedData.ALL_EXERCISES.filter {
                            it.id != currentEx.id && it.primaryMuscle == currentEx.primaryMuscle
                        }
                    }
                    Column {
                        availableReplacements.forEach { repEx ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.replaceCurrentExercise(repEx, userProfile)
                                        showReplaceDialog = false
                                    }
                                    .padding(vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(repEx.name, color = TextPrimary, fontSize = 14.sp)
                                Text(repEx.equipmentRequired.displayName, color = NeonGold, fontSize = 11.sp)
                            }
                            Divider(color = CardBorderDark)
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showReplaceDialog = false }) {
                        Text("CANCEL", color = TextSecondary)
                    }
                }
            )
        }
    }
}
