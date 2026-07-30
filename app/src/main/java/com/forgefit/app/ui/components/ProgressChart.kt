package com.forgefit.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.forgefit.app.ui.theme.*

@Composable
fun ProgressChart(
    dataPoints: List<Float>,
    labels: List<String>,
    modifier: Modifier = Modifier,
    unitLabel: String = "KG"
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceDark)
            .padding(16.dp)
    ) {
        if (dataPoints.isEmpty() || dataPoints.all { it == 0f }) {
            Text(
                text = "No workout volume data logged yet",
                color = TextSecondary,
                fontSize = 13.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            val maxVal = (dataPoints.maxOrNull() ?: 100f).coerceAtLeast(10f)

            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height - 30f // Leave space for labels
                val barWidth = (w / dataPoints.size) * 0.5f
                val spacing = (w / dataPoints.size)

                dataPoints.forEachIndexed { index, value ->
                    val barHeight = (value / maxVal) * h
                    val x = index * spacing + (spacing - barWidth) / 2f
                    val y = h - barHeight

                    // Bar gradient fill
                    drawRoundRect(
                        color = NeonGold,
                        topLeft = Offset(x, y),
                        size = Size(barWidth, barHeight),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
                    )
                }

                // Grid base line
                drawLine(
                    color = CardBorderDark,
                    start = Offset(0f, h),
                    end = Offset(w, h),
                    strokeWidth = 2f
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                labels.forEach { label ->
                    Text(
                        text = label,
                        color = TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
