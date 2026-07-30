package com.forgefit.app.ui.screens.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.forgefit.app.data.model.Exercise
import com.forgefit.app.data.seed.ExerciseSeedData
import com.forgefit.app.ui.components.EquipmentBadge
import com.forgefit.app.ui.components.ExerciseAnimationCanvas
import com.forgefit.app.ui.components.MuscleBadge
import com.forgefit.app.ui.theme.*

@Composable
fun ExerciseDetailScreen(
    exercise: Exercise,
    onBack: () -> Unit
) {
    val alternatives = remember(exercise) {
        ExerciseSeedData.ALL_EXERCISES.filter {
            it.id != exercise.id && (it.primaryMuscle == exercise.primaryMuscle || it.secondaryMuscles.contains(exercise.primaryMuscle))
        }.take(3)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Back Button & Title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextPrimary)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = exercise.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ANIMATED EXERCISE DEMONSTRATION CANVAS
        ExerciseAnimationCanvas(
            animationKey = exercise.animationKey,
            primaryMuscle = exercise.primaryMuscle,
            heightDp = 220
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Quick Stats Badges (Sets, Reps, Rest, Difficulty)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DetailStatBox(title = "SETS", value = "${exercise.defaultSets}", modifier = Modifier.weight(1f))
            DetailStatBox(title = "REPS", value = "${exercise.minReps}–${exercise.maxReps}", modifier = Modifier.weight(1f))
            DetailStatBox(title = "REST", value = "${exercise.restTimeSeconds}s", modifier = Modifier.weight(1f))
            DetailStatBox(title = "LEVEL", value = exercise.difficulty.displayName, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Instructions Section
        Text("INSTRUCTIONS", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = NeonGold, letterSpacing = 1.sp)
        Spacer(modifier = Modifier.height(8.dp))
        exercise.instructions.forEachIndexed { index, step ->
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "${index + 1}. ",
                    fontWeight = FontWeight.Bold,
                    color = NeonGold,
                    fontSize = 14.sp
                )
                Text(
                    text = step,
                    fontSize = 14.sp,
                    color = TextPrimary,
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Form Tips
        Text("FORM TIPS", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = ElectricBlue, letterSpacing = 1.sp)
        Spacer(modifier = Modifier.height(8.dp))
        exercise.formTips.forEach { tip ->
            Row(modifier = Modifier.padding(vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = ElectricBlue, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(tip, fontSize = 13.sp, color = TextSecondary)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Common Mistakes & Safety
        Text("COMMON MISTAKES & SAFETY", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SoftRed, letterSpacing = 1.sp)
        Spacer(modifier = Modifier.height(8.dp))
        exercise.commonMistakes.forEach { mistake ->
            Row(modifier = Modifier.padding(vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Warning, contentDescription = null, tint = SoftRed, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(mistake, fontSize = 13.sp, color = TextSecondary)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Smart Exercise Alternatives
        if (alternatives.isNotEmpty()) {
            Text("SMART EXERCISE ALTERNATIVES", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(8.dp))
            alternatives.forEach { alt ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceDark)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(alt.name, fontWeight = FontWeight.Bold, color = TextPrimary, modifier = Modifier.weight(1f))
                        EquipmentBadge(equipment = alt.equipmentRequired)
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailStatBox(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(horizontal = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextMuted)
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = NeonGold)
        }
    }
}
