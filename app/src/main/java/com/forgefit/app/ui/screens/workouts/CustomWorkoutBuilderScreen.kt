package com.forgefit.app.ui.screens.workouts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
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
import com.forgefit.app.ui.theme.*

@Composable
fun CustomWorkoutBuilderScreen(
    onSaveWorkout: (Workout) -> Unit,
    onBack: () -> Unit
) {
    var workoutName by remember { mutableStateOf("") }
    val selectedExerciseIds = remember { mutableStateListOf<String>() }
    var showAddDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "BUILD CUSTOM ROUTINE",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary
            )

            Button(
                onClick = {
                    if (workoutName.isNotBlank() && selectedExerciseIds.isNotEmpty()) {
                        val custom = Workout(
                            id = "custom_${System.currentTimeMillis()}",
                            name = workoutName,
                            description = "User created custom 2-dumbbell workout",
                            difficulty = Difficulty.INTERMEDIATE,
                            estimatedDurationMinutes = selectedExerciseIds.size * 8,
                            targetMuscles = emptyList(),
                            exerciseIds = selectedExerciseIds.toList(),
                            isCustom = true
                        )
                        onSaveWorkout(custom)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonGold),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.Save, contentDescription = null, tint = BackgroundDark, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("SAVE", color = BackgroundDark, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = workoutName,
            onValueChange = { workoutName = it },
            label = { Text("Workout Routine Name (e.g. My Arm Blast)") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = NeonGold,
                unfocusedBorderColor = CardBorderDark,
                focusedLabelColor = NeonGold
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Exercises (${selectedExerciseIds.size})", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            TextButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = null, tint = NeonGold)
                Spacer(modifier = Modifier.width(4.dp))
                Text("ADD EXERCISE", color = NeonGold, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(selectedExerciseIds) { id ->
                val ex = ExerciseSeedData.ALL_EXERCISES.find { it.id == id }
                ex?.let { exercise ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceDark)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(exercise.name, fontWeight = FontWeight.Bold, color = TextPrimary)
                                Text("${exercise.primaryMuscle.displayName} • ${exercise.defaultSets} Sets × ${exercise.minReps}–${exercise.maxReps} Reps", fontSize = 11.sp, color = TextSecondary)
                            }
                            IconButton(onClick = { selectedExerciseIds.remove(id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Remove", tint = SoftRed)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            containerColor = SurfaceDark,
            title = { Text("Select Exercise", fontWeight = FontWeight.Bold, color = TextPrimary) },
            text = {
                LazyColumn(modifier = Modifier.height(300.dp)) {
                    items(ExerciseSeedData.ALL_EXERCISES) { ex ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (!selectedExerciseIds.contains(ex.id)) {
                                        selectedExerciseIds.add(ex.id)
                                    }
                                    showAddDialog = false
                                }
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(ex.name, color = TextPrimary, fontSize = 14.sp)
                            Text(ex.primaryMuscle.displayName, color = NeonGold, fontSize = 11.sp)
                        }
                        Divider(color = CardBorderDark)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("CANCEL", color = TextSecondary)
                }
            }
        )
    }
}
