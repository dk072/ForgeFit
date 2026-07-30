package com.forgefit.app.ui.screens.workouts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
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
import com.forgefit.app.data.repository.OverloadEngine
import com.forgefit.app.ui.theme.*

@Composable
fun SmartGeneratorScreen(
    userProfile: UserProfile,
    onWorkoutGenerated: (Workout) -> Unit,
    onBack: () -> Unit
) {
    val selectedMuscles = remember { mutableStateListOf<MuscleGroup>() }
    var selectedDifficulty by remember { mutableStateOf(userProfile.experienceLevel.toDifficulty()) }
    var durationMinutes by remember { mutableStateOf(userProfile.targetDurationMinutes) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = NeonGold, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "SMART WORKOUT GENERATOR",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary
            )
        }
        Text(
            text = "Build a customized 2-Dumbbell session in seconds",
            fontSize = 12.sp,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Target Muscles Selection
        Text("Target Muscles", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val muscleOptions = listOf(MuscleGroup.CHEST, MuscleGroup.BACK, MuscleGroup.SHOULDERS, MuscleGroup.BICEPS, MuscleGroup.TRICEPS, MuscleGroup.QUADRICEPS, MuscleGroup.ABS)
            muscleOptions.take(4).forEach { muscle ->
                val selected = selectedMuscles.contains(muscle)
                FilterChip(
                    selected = selected,
                    onClick = {
                        if (selected) selectedMuscles.remove(muscle) else selectedMuscles.add(muscle)
                    },
                    label = { Text(muscle.displayName, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = NeonGold,
                        selectedLabelColor = BackgroundDark
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Difficulty Selection
        Text("Difficulty Level", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Difficulty.values().forEach { diff ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .height(44.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (selectedDifficulty == diff) NeonGold else SurfaceDark)
                        .clickable { selectedDifficulty = diff },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = diff.displayName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (selectedDifficulty == diff) BackgroundDark else TextPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Duration Selection
        Text("Workout Duration", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            listOf(20, 30, 45, 60).forEach { mins ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .height(44.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (durationMinutes == mins) NeonGold else SurfaceDark)
                        .clickable { durationMinutes = mins },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$mins MIN",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (durationMinutes == mins) BackgroundDark else TextPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        Button(
            onClick = {
                val generated = OverloadEngine.generateSmartWorkout(
                    targetMuscles = selectedMuscles.toList(),
                    difficulty = selectedDifficulty,
                    durationMinutes = durationMinutes,
                    userProfile = userProfile
                )
                onWorkoutGenerated(generated)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = NeonGold),
            shape = RoundedCornerShape(14.dp)
        ) {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = BackgroundDark)
            Spacer(modifier = Modifier.width(8.dp))
            Text("GENERATE WORKOUT", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = BackgroundDark)
        }
    }
}

private fun ExperienceLevel.toDifficulty(): Difficulty = when (this) {
    ExperienceLevel.BEGINNER -> Difficulty.BEGINNER
    ExperienceLevel.INTERMEDIATE -> Difficulty.INTERMEDIATE
    ExperienceLevel.ADVANCED -> Difficulty.ADVANCED
}
