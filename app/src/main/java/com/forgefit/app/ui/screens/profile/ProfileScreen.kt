package com.forgefit.app.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.forgefit.app.data.model.*
import com.forgefit.app.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    onUpdateProfile: (UserProfile) -> Unit,
    onOpenEquipmentSettings: () -> Unit
) {
    var leftWeight by remember(userProfile) { mutableStateOf("${userProfile.leftDumbbellWeightKg}") }
    var rightWeight by remember(userProfile) { mutableStateOf("${userProfile.rightDumbbellWeightKg}") }
    var soundEnabled by remember(userProfile) { mutableStateOf(userProfile.soundEnabled) }
    var vibrationEnabled by remember(userProfile) { mutableStateOf(userProfile.vibrationEnabled) }

    var isCheckingUpdate by remember { mutableStateOf(false) }
    var showUpdateDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Person, contentDescription = null, tint = NeonGold, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "PROFILE & SETTINGS",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // DUMBBELL WEIGHT CONFIGURATION CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("DUMBBELL WEIGHT SETUP", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = NeonGold)
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = leftWeight,
                        onValueChange = {
                            leftWeight = it
                            it.toFloatOrNull()?.let { lw -> onUpdateProfile(userProfile.copy(leftDumbbellWeightKg = lw)) }
                        },
                        label = { Text("Left DB (${userProfile.weightUnit.name})") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = NeonGold, unfocusedBorderColor = CardBorderDark),
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = rightWeight,
                        onValueChange = {
                            rightWeight = it
                            it.toFloatOrNull()?.let { rw -> onUpdateProfile(userProfile.copy(rightDumbbellWeightKg = rw)) }
                        },
                        label = { Text("Right DB (${userProfile.weightUnit.name})") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = NeonGold, unfocusedBorderColor = CardBorderDark),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // EQUIPMENT SETTINGS LINK
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onOpenEquipmentSettings() },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Build, contentDescription = null, tint = ElectricBlue)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Equipment Settings", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 15.sp)
                        Text("${userProfile.enabledEquipment.size} Equipment items active", fontSize = 11.sp, color = TextSecondary)
                    }
                }
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // PREFERENCES & AUDIO / VIBRATION TOGGLES
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("TIMER & FEEDBACK", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextSecondary)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Timer Sound Effects", color = TextPrimary, fontSize = 14.sp)
                    Switch(
                        checked = soundEnabled,
                        onCheckedChange = {
                            soundEnabled = it
                            onUpdateProfile(userProfile.copy(soundEnabled = it))
                        },
                        colors = SwitchDefaults.colors(checkedThumbColor = NeonGold, checkedTrackColor = SurfaceVariantDark)
                    )
                }

                HorizontalDivider(color = CardBorderDark, modifier = Modifier.padding(vertical = 8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Rest Timer Haptic Vibration", color = TextPrimary, fontSize = 14.sp)
                    Switch(
                        checked = vibrationEnabled,
                        onCheckedChange = {
                            vibrationEnabled = it
                            onUpdateProfile(userProfile.copy(vibrationEnabled = it))
                        },
                        colors = SwitchDefaults.colors(checkedThumbColor = NeonGold, checkedTrackColor = SurfaceVariantDark)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // IN-APP UPDATE & ABOUT SECTION
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("APP UPDATES & SYSTEM", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = NeonGold)
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("ForgeFit App Version", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 15.sp)
                        Text("v1.1.0 (Build 2) • Up to date", fontSize = 12.sp, color = TextSecondary)
                    }

                    Surface(
                        color = SurfaceVariantDark,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "v1.1.0",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonGold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // CHECK FOR UPDATES BUTTON
                Button(
                    onClick = {
                        isCheckingUpdate = true
                        coroutineScope.launch {
                            delay(1200L) // Simulate network/system version check
                            isCheckingUpdate = false
                            showUpdateDialog = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !isCheckingUpdate
                ) {
                    if (isCheckingUpdate) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            color = NeonGold,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Checking for updates...", color = TextPrimary, fontSize = 13.sp)
                    } else {
                        Icon(Icons.Default.SystemUpdate, contentDescription = null, tint = NeonGold, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Check for Updates", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }
    }

    // UPDATE RESULT DIALOG
    if (showUpdateDialog) {
        AlertDialog(
            onDismissRequest = { showUpdateDialog = false },
            containerColor = SurfaceDark,
            shape = RoundedCornerShape(20.dp),
            icon = {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = NeonGreen,
                    modifier = Modifier.size(40.dp)
                )
            },
            title = {
                Text(
                    text = "App is Up to Date!",
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    fontSize = 18.sp
                )
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "ForgeFit – Dumbbell Training is running the latest version v1.1.0.",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "✨ What's New in v1.1.0:\n• Enhanced In-App Update Engine\n• Procedural 60 FPS movement biomechanics\n• Interactive anatomical muscle map\n• Smart fixed-dumbbell progressive overload",
                        fontSize = 12.sp,
                        color = TextMuted,
                        lineHeight = 18.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showUpdateDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonGold),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("OK", color = BackgroundDark, fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}
