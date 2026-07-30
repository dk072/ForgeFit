package com.forgefit.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.forgefit.app.data.model.MuscleGroup
import com.forgefit.app.ui.theme.*

enum class MapView { FRONT, BACK }

@Composable
fun MuscleMapCanvas(
    selectedMuscle: MuscleGroup?,
    onMuscleSelected: (MuscleGroup) -> Unit,
    modifier: Modifier = Modifier
) {
    var viewMode by remember { mutableStateOf(MapView.FRONT) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SurfaceDark)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Toggle view mode FRONT / BACK
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceVariantDark)
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (viewMode == MapView.FRONT) NeonGold else Color.Transparent)
                    .clickable { viewMode = MapView.FRONT }
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "FRONT VIEW",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (viewMode == MapView.FRONT) BackgroundDark else TextSecondary
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (viewMode == MapView.BACK) NeonGold else Color.Transparent)
                    .clickable { viewMode = MapView.BACK }
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "BACK VIEW",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (viewMode == MapView.BACK) BackgroundDark else TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Interactive Anatomical Drawing Canvas
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .pointerInput(viewMode) {
                    detectTapGestures { tapOffset ->
                        val w = size.width
                        val h = size.height
                        val cX = w / 2f

                        // Simple tap target region mapper for anatomical body diagram
                        if (viewMode == MapView.FRONT) {
                            when {
                                tapOffset.y in (h * 0.22f)..(h * 0.35f) && tapOffset.x in (cX - 60f)..(cX + 60f) -> onMuscleSelected(MuscleGroup.CHEST)
                                tapOffset.y in (h * 0.18f)..(h * 0.28f) -> onMuscleSelected(MuscleGroup.SHOULDERS)
                                tapOffset.y in (h * 0.32f)..(h * 0.48f) && (tapOffset.x < cX - 50f || tapOffset.x > cX + 50f) -> onMuscleSelected(MuscleGroup.BICEPS)
                                tapOffset.y in (h * 0.35f)..(h * 0.55f) && tapOffset.x in (cX - 45f)..(cX + 45f) -> onMuscleSelected(MuscleGroup.ABS)
                                tapOffset.y in (h * 0.48f)..(h * 0.62f) && (tapOffset.x < cX - 65f || tapOffset.x > cX + 65f) -> onMuscleSelected(MuscleGroup.FOREARMS)
                                tapOffset.y in (h * 0.55f)..(h * 0.78f) -> onMuscleSelected(MuscleGroup.QUADRICEPS)
                                tapOffset.y in (h * 0.78f)..(h * 0.95f) -> onMuscleSelected(MuscleGroup.CALVES)
                            }
                        } else {
                            when {
                                tapOffset.y in (h * 0.18f)..(h * 0.42f) && tapOffset.x in (cX - 65f)..(cX + 65f) -> onMuscleSelected(MuscleGroup.BACK)
                                tapOffset.y in (h * 0.25f)..(h * 0.45f) && (tapOffset.x < cX - 55f || tapOffset.x > cX + 55f) -> onMuscleSelected(MuscleGroup.TRICEPS)
                                tapOffset.y in (h * 0.45f)..(h * 0.60f) -> onMuscleSelected(MuscleGroup.GLUTES)
                                tapOffset.y in (h * 0.60f)..(h * 0.78f) -> onMuscleSelected(MuscleGroup.HAMSTRINGS)
                                tapOffset.y in (h * 0.78f)..(h * 0.95f) -> onMuscleSelected(MuscleGroup.CALVES)
                            }
                        }
                    }
                }
        ) {
            val w = size.width
            val h = size.height
            val cX = w / 2f

            val defaultBodyColor = Color(0xFF262C3D)
            val strokeColor = CardBorderDark

            // Head
            drawCircle(defaultBodyColor, radius = 24f, center = Offset(cX, h * 0.12f))
            drawCircle(strokeColor, radius = 24f, center = Offset(cX, h * 0.12f), style = Stroke(3f))

            if (viewMode == MapView.FRONT) {
                // Chest
                val chestActive = selectedMuscle == MuscleGroup.CHEST
                drawRoundRect(
                    color = if (chestActive) MuscleHighlightChest else defaultBodyColor,
                    topLeft = Offset(cX - 55f, h * 0.22f),
                    size = Size(110f, h * 0.12f),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f, 16f)
                )

                // Shoulders
                val deltActive = selectedMuscle == MuscleGroup.SHOULDERS
                drawCircle(if (deltActive) MuscleHighlightShoulders else defaultBodyColor, radius = 26f, center = Offset(cX - 70f, h * 0.24f))
                drawCircle(if (deltActive) MuscleHighlightShoulders else defaultBodyColor, radius = 26f, center = Offset(cX + 70f, h * 0.24f))

                // Biceps
                val bicepActive = selectedMuscle == MuscleGroup.BICEPS
                drawRoundRect(if (bicepActive) MuscleHighlightArms else defaultBodyColor, topLeft = Offset(cX - 95f, h * 0.32f), size = Size(26f, h * 0.14f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(10f))
                drawRoundRect(if (bicepActive) MuscleHighlightArms else defaultBodyColor, topLeft = Offset(cX + 69f, h * 0.32f), size = Size(26f, h * 0.14f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(10f))

                // Abs
                val absActive = selectedMuscle == MuscleGroup.ABS
                drawRoundRect(if (absActive) MuscleHighlightAbs else defaultBodyColor, topLeft = Offset(cX - 42f, h * 0.36f), size = Size(84f, h * 0.18f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f))

                // Quads
                val quadActive = selectedMuscle == MuscleGroup.QUADRICEPS
                drawRoundRect(if (quadActive) MuscleHighlightLegs else defaultBodyColor, topLeft = Offset(cX - 50f, h * 0.56f), size = Size(45f, h * 0.22f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(14f))
                drawRoundRect(if (quadActive) MuscleHighlightLegs else defaultBodyColor, topLeft = Offset(cX + 5f, h * 0.56f), size = Size(45f, h * 0.22f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(14f))

                // Calves
                val calfActive = selectedMuscle == MuscleGroup.CALVES
                drawRoundRect(if (calfActive) MuscleHighlightLegs else defaultBodyColor, topLeft = Offset(cX - 45f, h * 0.80f), size = Size(38f, h * 0.14f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f))
                drawRoundRect(if (calfActive) MuscleHighlightLegs else defaultBodyColor, topLeft = Offset(cX + 7f, h * 0.80f), size = Size(38f, h * 0.14f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f))
            } else {
                // Back & Lats
                val backActive = selectedMuscle == MuscleGroup.BACK
                drawRoundRect(if (backActive) MuscleHighlightBack else defaultBodyColor, topLeft = Offset(cX - 60f, h * 0.20f), size = Size(120f, h * 0.24f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f))

                // Triceps
                val triActive = selectedMuscle == MuscleGroup.TRICEPS
                drawRoundRect(if (triActive) MuscleHighlightArms else defaultBodyColor, topLeft = Offset(cX - 95f, h * 0.26f), size = Size(26f, h * 0.16f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(10f))
                drawRoundRect(if (triActive) MuscleHighlightArms else defaultBodyColor, topLeft = Offset(cX + 69f, h * 0.26f), size = Size(26f, h * 0.16f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(10f))

                // Glutes
                val gluteActive = selectedMuscle == MuscleGroup.GLUTES
                drawRoundRect(if (gluteActive) MuscleHighlightLegs else defaultBodyColor, topLeft = Offset(cX - 52f, h * 0.46f), size = Size(104f, h * 0.14f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(14f))

                // Hamstrings
                val hamActive = selectedMuscle == MuscleGroup.HAMSTRINGS
                drawRoundRect(if (hamActive) MuscleHighlightLegs else defaultBodyColor, topLeft = Offset(cX - 48f, h * 0.62f), size = Size(44f, h * 0.17f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f))
                drawRoundRect(if (hamActive) MuscleHighlightLegs else defaultBodyColor, topLeft = Offset(cX + 4f, h * 0.62f), size = Size(44f, h * 0.17f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f))

                // Calves
                val calfActive = selectedMuscle == MuscleGroup.CALVES
                drawRoundRect(if (calfActive) MuscleHighlightLegs else defaultBodyColor, topLeft = Offset(cX - 45f, h * 0.80f), size = Size(38f, h * 0.14f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f))
                drawRoundRect(if (calfActive) MuscleHighlightLegs else defaultBodyColor, topLeft = Offset(cX + 7f, h * 0.80f), size = Size(38f, h * 0.14f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Selected muscle indicator pill
        Surface(
            color = SurfaceVariantDark,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = selectedMuscle?.displayName?.uppercase() ?: "TAP A MUSCLE TO FILTER EXERCISES",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (selectedMuscle != null) NeonGold else TextSecondary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}
