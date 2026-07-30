package com.forgefit.app.ui.screens.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.forgefit.app.data.model.*
import com.forgefit.app.ui.components.ExerciseCard
import com.forgefit.app.ui.theme.*

@Composable
fun ExerciseListScreen(
    exercises: List<Exercise>,
    onExerciseClick: (Exercise) -> Unit,
    onFavoriteToggle: (String, Boolean) -> Unit,
    onOpenMuscleMap: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedMuscleFilter by remember { mutableStateOf<MuscleGroup?>(null) }
    var selectedEquipmentFilter by remember { mutableStateOf<Equipment?>(null) }

    val filteredExercises = exercises.filter { ex ->
        (searchQuery.isEmpty() || ex.name.contains(searchQuery, ignoreCase = true)) &&
                (selectedMuscleFilter == null || ex.primaryMuscle == selectedMuscleFilter) &&
                (selectedEquipmentFilter == null || ex.equipmentRequired == selectedEquipmentFilter)
    }

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
            Column {
                Text(
                    text = "EXERCISE LIBRARY",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextPrimary
                )
                Text(
                    text = "${filteredExercises.size} Dumbbell & bodyweight movements",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            // Interactive Muscle Map Button
            IconButton(
                onClick = onOpenMuscleMap,
                modifier = Modifier
                    .background(SurfaceVariantDark, shape = RoundedCornerShape(12.dp))
                    .padding(4.dp)
            ) {
                Icon(Icons.Default.AccessibilityNew, contentDescription = "Muscle Map", tint = NeonGold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search exercises...", color = TextMuted) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextSecondary) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = NeonGold,
                unfocusedBorderColor = CardBorderDark,
                focusedContainerColor = SurfaceDark,
                unfocusedContainerColor = SurfaceDark
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Muscle Filters Chips
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            item {
                FilterChip(
                    selected = selectedMuscleFilter == null,
                    onClick = { selectedMuscleFilter = null },
                    label = { Text("All Muscles", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = NeonGold, selectedLabelColor = BackgroundDark)
                )
            }
            items(MuscleGroup.values()) { muscle ->
                FilterChip(
                    selected = selectedMuscleFilter == muscle,
                    onClick = { selectedMuscleFilter = if (selectedMuscleFilter == muscle) null else muscle },
                    label = { Text(muscle.displayName, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = NeonGold, selectedLabelColor = BackgroundDark)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(filteredExercises) { exercise ->
                ExerciseCard(
                    exercise = exercise,
                    onCardClick = { onExerciseClick(exercise) },
                    onFavoriteClick = { onFavoriteToggle(exercise.id, exercise.isFavorite) }
                )
            }
        }
    }
}
