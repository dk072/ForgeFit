package com.forgefit.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.forgefit.app.data.model.MuscleGroup
import com.forgefit.app.ui.theme.*

@Composable
fun ExerciseAnimationCanvas(
    animationKey: String,
    primaryMuscle: MuscleGroup,
    modifier: Modifier = Modifier,
    heightDp: Int = 220
) {
    var isPlaying by remember { mutableStateOf(true) }

    val infiniteTransition = rememberInfiniteTransition(label = "exercise_anim")
    val progress by if (isPlaying) {
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 2400, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "movement_progress"
        )
    } else {
        remember { mutableStateOf(0.5f) }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(heightDp.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceDark),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val w = size.width
            val h = size.height

            // Background subtle grid lines
            val gridColor = Color(0xFF1E2333)
            for (i in 1..4) {
                drawLine(gridColor, Offset(0f, h * (i / 5f)), Offset(w, h * (i / 5f)), strokeWidth = 2f)
            }

            // Draw floor / bench line
            val floorY = h * 0.78f
            drawLine(
                color = CardBorderDark,
                start = Offset(w * 0.1f, floorY),
                end = Offset(w * 0.9f, floorY),
                strokeWidth = 6f,
                cap = StrokeCap.Round
            )

            val headRadius = 22f
            val torsoLength = 90f
            val armLength = 60f

            // Muscle highlight color
            val muscleColor = when (primaryMuscle) {
                MuscleGroup.CHEST -> MuscleHighlightChest
                MuscleGroup.BACK -> MuscleHighlightBack
                MuscleGroup.SHOULDERS -> MuscleHighlightShoulders
                MuscleGroup.BICEPS, MuscleGroup.TRICEPS, MuscleGroup.FOREARMS -> MuscleHighlightArms
                MuscleGroup.QUADRICEPS, MuscleGroup.HAMSTRINGS, MuscleGroup.GLUTES, MuscleGroup.CALVES -> MuscleHighlightLegs
                MuscleGroup.ABS -> MuscleHighlightAbs
            }

            when (animationKey) {
                "anim_floor_press", "anim_squeeze_press", "anim_single_arm_floor_press" -> {
                    // Person lying down on floor
                    val bodyY = floorY - 20f
                    val headX = center.x - 100f
                    val shoulderX = headX + 40f
                    val hipX = shoulderX + 110f

                    // Head & Torso
                    drawCircle(Color.LightGray, radius = headRadius, center = Offset(headX, bodyY - 10f))
                    drawLine(Color.LightGray, Offset(headX, bodyY - 10f), Offset(hipX, bodyY - 10f), strokeWidth = 24f, cap = StrokeCap.Round)
                    // Chest highlight
                    drawCircle(muscleColor, radius = 28f * (0.8f + progress * 0.4f), center = Offset(shoulderX + 30f, bodyY - 10f), alpha = 0.5f)

                    // Arms moving up/down with dumbbells
                    val elbowY = bodyY - 10f - ((1f - progress) * 35f)
                    val handY = bodyY - 80f - (progress * 70f)

                    // Left/Right Dumbbell & Arm
                    drawLine(muscleColor, Offset(shoulderX + 20f, bodyY - 10f), Offset(shoulderX + 20f, elbowY), strokeWidth = 14f, cap = StrokeCap.Round)
                    drawLine(Color.LightGray, Offset(shoulderX + 20f, elbowY), Offset(shoulderX + 20f, handY), strokeWidth = 12f, cap = StrokeCap.Round)

                    // Dumbbell
                    drawRect(NeonGold, Offset(shoulderX - 5f, handY - 12f), Size(50f, 24f))
                    drawRect(Color.White, Offset(shoulderX + 15f, handY - 6f), Size(10f, 12f))
                }

                "anim_goblet_squat", "anim_db_lunge", "anim_bulgarian_squat" -> {
                    // Standing person squatting
                    val kneeY = floorY - 70f + (progress * 40f)
                    val hipY = kneeY - 80f + (progress * 50f)
                    val headY = hipY - 90f

                    // Head & Torso
                    drawCircle(Color.LightGray, radius = headRadius, center = Offset(center.x, headY))
                    drawLine(Color.LightGray, Offset(center.x, headY + headRadius), Offset(center.x, hipY), strokeWidth = 26f, cap = StrokeCap.Round)

                    // Legs bending
                    val footLeftX = center.x - 35f
                    val footRightX = center.x + 35f
                    val kneeLeftX = center.x - 55f - (progress * 20f)
                    val kneeRightX = center.x + 55f + (progress * 20f)

                    // Leg muscles highlight
                    drawLine(muscleColor, Offset(center.x, hipY), Offset(kneeLeftX, kneeY), strokeWidth = 20f, cap = StrokeCap.Round)
                    drawLine(muscleColor, Offset(center.x, hipY), Offset(kneeRightX, kneeY), strokeWidth = 20f, cap = StrokeCap.Round)
                    drawLine(Color.LightGray, Offset(kneeLeftX, kneeY), Offset(footLeftX, floorY), strokeWidth = 14f, cap = StrokeCap.Round)
                    drawLine(Color.LightGray, Offset(kneeRightX, kneeY), Offset(footRightX, floorY), strokeWidth = 14f, cap = StrokeCap.Round)

                    // Goblet dumbbell held at chest
                    val dbY = headY + 50f
                    drawRect(NeonGold, Offset(center.x - 18f, dbY), Size(36f, 40f))
                }

                "anim_db_curl", "anim_hammer_curl", "anim_zottman_curl", "anim_concentration_curl" -> {
                    // Arm curling animation
                    val shoulderX = center.x - 40f
                    val shoulderY = center.y - 70f
                    val elbowX = shoulderX
                    val elbowY = shoulderY + 80f

                    // Forearm angle from 90 deg (hanging down) to 170 deg (curled up)
                    val angleRad = (Math.PI / 2.0) + (progress * Math.PI * 0.65)
                    val handX = elbowX + (Math.cos(angleRad) * 80f).toFloat()
                    val handY = elbowY - (Math.sin(angleRad) * 80f).toFloat()

                    // Torso
                    drawCircle(Color.LightGray, radius = headRadius, center = Offset(shoulderX - 30f, shoulderY - 30f))
                    drawLine(Color.LightGray, Offset(shoulderX - 30f, shoulderY), Offset(shoulderX - 30f, shoulderY + 120f), strokeWidth = 28f)

                    // Biceps Muscle Glow
                    drawCircle(muscleColor, radius = 24f * (0.8f + progress * 0.5f), center = Offset(shoulderX + 5f, shoulderY + 40f), alpha = 0.8f)

                    // Upper Arm & Forearm
                    drawLine(Color.LightGray, Offset(shoulderX, shoulderY), Offset(elbowX, elbowY), strokeWidth = 18f, cap = StrokeCap.Round)
                    drawLine(muscleColor, Offset(elbowX, elbowY), Offset(handX, handY), strokeWidth = 16f, cap = StrokeCap.Round)

                    // Dumbbell
                    drawRect(NeonGold, Offset(handX - 16f, handY - 16f), Size(32f, 32f))
                }

                else -> {
                    // Default Push-Up / General movement visualizer
                    val YOffset = progress * 35f
                    val torsoY = center.y + YOffset

                    drawCircle(Color.LightGray, radius = headRadius, center = Offset(center.x - 120f, torsoY - 20f))
                    drawLine(Color.LightGray, Offset(center.x - 100f, torsoY), Offset(center.x + 80f, torsoY + 20f), strokeWidth = 24f, cap = StrokeCap.Round)

                    // Muscle glow
                    drawCircle(muscleColor, radius = 30f, center = Offset(center.x - 40f, torsoY), alpha = 0.7f * progress)

                    // Hands & Legs
                    drawLine(muscleColor, Offset(center.x - 60f, torsoY), Offset(center.x - 60f, floorY), strokeWidth = 14f)
                    drawLine(Color.LightGray, Offset(center.x + 80f, torsoY + 20f), Offset(center.x + 120f, floorY), strokeWidth = 16f)
                }
            }

            // Phase indicator overlay text in canvas
            val phaseText = if (progress < 0.5f) "START → MOVEMENT" else "PEAK CONTRACTION → RETURN"
        }

        // Overlay Play/Pause Controls & Movement Phase Badge
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = SurfaceVariantDark.copy(alpha = 0.9f),
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (isPlaying) NeonGreen else NeonGold)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (progress < 0.5f) "CONCENTRIC PHASE" else "ECCENTRIC PHASE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
            }

            IconButton(
                onClick = { isPlaying = !isPlaying },
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(SurfaceVariantDark)
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = "Toggle Animation",
                    tint = NeonGold,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
