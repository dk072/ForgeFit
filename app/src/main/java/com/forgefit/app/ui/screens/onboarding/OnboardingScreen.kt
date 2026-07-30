package com.forgefit.app.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.forgefit.app.data.model.*
import com.forgefit.app.ui.theme.*

@Composable
fun OnboardingScreen(
    onOnboardingComplete: (UserProfile) -> Unit
) {
    var step by remember { mutableStateOf(1) }
    var experienceLevel by remember { mutableStateOf(ExperienceLevel.BEGINNER) }
    var goal by remember { mutableStateOf(Goal.BUILD_MUSCLE) }
    var workoutDays by remember { mutableStateOf(3) }
    var durationMinutes by remember { mutableStateOf(45) }
    var dumbbellType by remember { mutableStateOf(DumbbellType.FIXED) }
    var leftWeightText by remember { mutableStateOf("5.0") }
    var rightWeightText by remember { mutableStateOf("5.0") }
    var weightUnit by remember { mutableStateOf(WeightUnit.KG) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Brand Logo Header
        Icon(
            imageVector = Icons.Default.FitnessCenter,
            contentDescription = null,
            tint = NeonGold,
            modifier = Modifier.size(56.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "FORGEFIT",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = NeonGold,
            letterSpacing = 2.sp
        )
        Text(
            text = "2-Dumbbell & Bodyweight Training",
            fontSize = 13.sp,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        when (step) {
            1 -> {
                Text("Select Experience Level", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Spacer(modifier = Modifier.height(16.dp))
                ExperienceLevel.values().forEach { level ->
                    SelectableCard(
                        title = level.displayName,
                        selected = experienceLevel == level,
                        onClick = { experienceLevel = level }
                    )
                }
            }
            2 -> {
                Text("What is your primary goal?", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Spacer(modifier = Modifier.height(16.dp))
                Goal.values().forEach { g ->
                    SelectableCard(
                        title = g.displayName,
                        selected = goal == g,
                        onClick = { goal = g }
                    )
                }
            }
            3 -> {
                Text("How many days per week?", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf(2, 3, 4, 5, 6).forEach { days ->
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (workoutDays == days) NeonGold else SurfaceDark)
                                .clickable { workoutDays = days },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$days",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (workoutDays == days) BackgroundDark else TextPrimary
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
                Text("Target Workout Duration", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf(20, 30, 45, 60).forEach { mins ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp)
                                .height(46.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (durationMinutes == mins) NeonGold else SurfaceDark)
                                .clickable { durationMinutes = mins },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$mins min",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (durationMinutes == mins) BackgroundDark else TextPrimary
                            )
                        }
                    }
                }
            }
            4 -> {
                Text("Dumbbell Setup", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text("Tell us about your dumbbells", fontSize = 13.sp, color = TextSecondary)
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    DumbbellType.values().forEach { type ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp)
                                .height(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (dumbbellType == type) NeonGold else SurfaceDark)
                                .clickable { dumbbellType = type },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = type.displayName,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (dumbbellType == type) BackgroundDark else TextPrimary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Unit", fontSize = 14.sp, color = TextSecondary)
                    Row {
                        WeightUnit.values().forEach { unit ->
                            Surface(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .clickable { weightUnit = unit },
                                color = if (weightUnit == unit) NeonGold else SurfaceDark,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = unit.name,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (weightUnit == unit) BackgroundDark else TextPrimary,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = leftWeightText,
                    onValueChange = { leftWeightText = it },
                    label = { Text("Left Dumbbell Weight (${weightUnit.name})") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonGold,
                        unfocusedBorderColor = CardBorderDark,
                        focusedLabelColor = NeonGold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = rightWeightText,
                    onValueChange = { rightWeightText = it },
                    label = { Text("Right Dumbbell Weight (${weightUnit.name})") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonGold,
                        unfocusedBorderColor = CardBorderDark,
                        focusedLabelColor = NeonGold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(32.dp))

        // Navigation Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (step > 1) {
                OutlinedButton(
                    onClick = { step -= 1 },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("BACK")
                }
            } else {
                Spacer(modifier = Modifier.width(1.dp))
            }

            Button(
                onClick = {
                    if (step < 4) {
                        step += 1
                    } else {
                        val leftKg = leftWeightText.toFloatOrNull() ?: 5.0f
                        val rightKg = rightWeightText.toFloatOrNull() ?: 5.0f
                        val profile = UserProfile(
                            experienceLevel = experienceLevel,
                            goal = goal,
                            workoutDaysPerWeek = workoutDays,
                            targetDurationMinutes = durationMinutes,
                            dumbbellType = dumbbellType,
                            leftDumbbellWeightKg = leftKg,
                            rightDumbbellWeightKg = rightKg,
                            weightUnit = weightUnit,
                            isOnboarded = true
                        )
                        onOnboardingComplete(profile)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonGold),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (step == 4) "START FORGING" else "CONTINUE",
                    fontWeight = FontWeight.Bold,
                    color = BackgroundDark
                )
            }
        }
    }
}

@Composable
private fun SelectableCard(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) SurfaceVariantDark else SurfaceDark
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (selected) NeonGold else TextPrimary,
                modifier = Modifier.weight(1f)
            )
            RadioButton(
                selected = selected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(selectedColor = NeonGold)
            )
        }
    }
}
