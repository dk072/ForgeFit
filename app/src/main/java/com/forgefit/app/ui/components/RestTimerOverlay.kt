package com.forgefit.app.ui.components

import android.content.Context
import android.media.ToneGenerator
import android.media.AudioManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.Build
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.forgefit.app.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun RestTimerOverlay(
    totalSeconds: Int,
    onRestFinished: () -> Unit,
    soundEnabled: Boolean = true,
    vibrationEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var remainingSeconds by remember { mutableStateOf(totalSeconds) }
    val progress = if (totalSeconds > 0) remainingSeconds.toFloat() / totalSeconds.toFloat() else 0f
    val animatedProgress by animateFloatAsState(targetValue = progress, animationSpec = tween(500), label = "timer")

    LaunchedEffect(remainingSeconds) {
        if (remainingSeconds > 0) {
            delay(1000L)
            remainingSeconds -= 1
        } else {
            // Trigger sound tone & vibration when timer hits zero
            if (soundEnabled) {
                try {
                    val toneGen = ToneGenerator(AudioManager.STREAM_ALARM, 80)
                    toneGen.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 400)
                } catch (e: Exception) {}
            }
            if (vibrationEnabled) {
                try {
                    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                    if (vibrator?.hasVibrator() == true) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                        } else {
                            vibrator.vibrate(500)
                        }
                    }
                } catch (e: Exception) {}
            }
            onRestFinished()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark.copy(alpha = 0.95f))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(0.9f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "REST & RECOVER",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonGold,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Circular Countdown Progress Bar
                Box(
                    modifier = Modifier.size(180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val diameter = size.minDimension
                        val stroke = 14.dp.toPx()

                        // Background ring
                        drawArc(
                            color = CardBorderDark,
                            startAngle = 0f,
                            sweepAngle = 360f,
                            useCenter = false,
                            style = Stroke(stroke, cap = StrokeCap.Round)
                        )

                        // Countdown Progress Ring
                        drawArc(
                            color = NeonGold,
                            startAngle = -90f,
                            sweepAngle = 360f * animatedProgress,
                            useCenter = false,
                            style = Stroke(stroke, cap = StrokeCap.Round)
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val mins = remainingSeconds / 60
                        val secs = remainingSeconds % 60
                        Text(
                            text = String.format("%02d:%02d", mins, secs),
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Remaining",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // +15 SECONDS BUTTON
                    Button(
                        onClick = { remainingSeconds += 15 },
                        colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = NeonGold)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("+15 sec", color = TextPrimary)
                    }

                    // SKIP REST BUTTON
                    Button(
                        onClick = onRestFinished,
                        colors = ButtonDefaults.buttonColors(containerColor = NeonGold),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(Icons.Default.SkipNext, contentDescription = null, tint = BackgroundDark)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Skip Rest", color = BackgroundDark, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
