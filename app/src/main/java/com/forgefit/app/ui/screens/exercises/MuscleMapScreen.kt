package com.forgefit.app.ui.screens.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.forgefit.app.data.model.Exercise
import com.forgefit.app.data.model.MuscleGroup
import com.forgefit.app.ui.components.ExerciseCard
import com.forgefit.app.ui.components.MuscleMapCanvas
import com.forgefit.app.ui.theme.*

@Composable
fun MuscleMapScreen(
    exercises: List<Exercise>,
    onExerciseClick: (Exercise) -> Unit,
    onFavoriteToggle: (String, Boolean) -> Unit,
    onBack: () -> Unit
) {
    var selectedMuscle by remember { mutableStateOf<MuscleGroup?>(MuscleGroup.CHEST) }

    val targetedExercises = remember(selectedMuscle, exercises) {
        if (selectedMuscle == null) exercises else exercises.filter {
            it.primaryMuscle == selectedMuscle || it.secondaryMuscles.contains(selectedMuscle)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextPrimary)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "ANATOMICAL MUSCLE MAP",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        MuscleMapCanvas(
            selectedMuscle = selectedMuscle,
            onMuscleSelected = { selectedMuscle = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "TARGETED EXERCISES (${targetedExercises.size})",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = TextSecondary,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(targetedExercises) { exercise ->
                ExerciseCard(
                    exercise = exercise,
                    onCardClick = { onExerciseClick(exercise) },
                    onFavoriteClick = { onFavoriteToggle(exercise.id, exercise.isFavorite) }
                )
            }
        }
    }
}
