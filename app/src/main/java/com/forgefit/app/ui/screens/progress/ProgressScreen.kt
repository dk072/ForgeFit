package com.forgefit.app.ui.screens.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.forgefit.app.data.model.PersonalRecord
import com.forgefit.app.data.model.WorkoutSession
import com.forgefit.app.ui.components.ProgressChart
import com.forgefit.app.ui.components.StatCard
import com.forgefit.app.ui.theme.*

@Composable
fun ProgressScreen(
    sessions: List<WorkoutSession>,
    personalRecords: List<PersonalRecord>,
    onNavigateToHistory: () -> Unit
) {
    var selectedTimeframe by remember { mutableStateOf("Weekly") }

    val totalWorkouts = sessions.size
    val totalTimeMinutes = sessions.sumOf { it.durationSeconds } / 60
    val totalVolumeKg = sessions.sumOf { it.totalVolumeKg.toDouble() }.toFloat()
    val totalSets = sessions.sumOf { it.totalSets }
    val totalReps = sessions.sumOf { it.totalReps }

    // Chart mock dataset calculation based on sessions
    val chartData = remember(sessions, selectedTimeframe) {
        if (sessions.isEmpty()) listOf(0f, 0f, 0f, 0f, 0f, 0f, 0f)
        else listOf(1200f, 2400f, 1800f, 3200f, 2900f, 4100f, totalVolumeKg.coerceAtLeast(4500f))
    }
    val chartLabels = when (selectedTimeframe) {
        "Weekly" -> listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        "Monthly" -> listOf("W1", "W2", "W3", "W4")
        else -> listOf("Q1", "Q2", "Q3", "Q4")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "PROGRESS & ANALYTICS",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextPrimary
                )
                Text(
                    text = "Track volume, sets, and personal records",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            IconButton(
                onClick = onNavigateToHistory,
                modifier = Modifier
                    .background(SurfaceVariantDark, shape = RoundedCornerShape(12.dp))
                    .padding(4.dp)
            ) {
                Icon(Icons.Default.History, contentDescription = "History", tint = NeonGold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // STATS GRID
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StatCard(title = "WORKOUTS", value = "$totalWorkouts", subtitle = "Completed", modifier = Modifier.weight(1f))
            StatCard(title = "TOTAL TIME", value = "${totalTimeMinutes}m", subtitle = "In Gym", modifier = Modifier.weight(1f))
            StatCard(title = "VOLUME", value = "${totalVolumeKg.toInt()}kg", subtitle = "Lifted", modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // VOLUME PROGRESS CHART & TIMEFRAME SELECTOR
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("VOLUME ANALYTICS", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextSecondary, letterSpacing = 1.sp)
            Row {
                listOf("Weekly", "Monthly", "1 Year").forEach { timeframe ->
                    Surface(
                        color = if (selectedTimeframe == timeframe) NeonGold else SurfaceDark,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .clickable { selectedTimeframe = timeframe }
                    ) {
                        Text(
                            text = timeframe,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (selectedTimeframe == timeframe) BackgroundDark else TextSecondary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        ProgressChart(
            dataPoints = chartData,
            labels = chartLabels
        )

        Spacer(modifier = Modifier.height(24.dp))

        // PERSONAL RECORDS SECTION
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = NeonGold, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("PERSONAL RECORDS (PRs)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (personalRecords.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark)
            ) {
                Text(
                    text = "No PRs logged yet. Complete workouts to earn personal record trophies!",
                    fontSize = 13.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            personalRecords.forEach { pr ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceDark)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(14.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(pr.exerciseName, fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 14.sp)
                            Text("Max Set Volume: ${pr.maxVolumeSingleSetKg.toInt()} kg", fontSize = 11.sp, color = TextSecondary)
                        }

                        Surface(
                            color = NeonGold.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "${pr.maxWeightKg} KG × ${pr.maxReps} REPS",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = NeonGold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
