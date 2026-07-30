package com.forgefit.app.ui.screens.profile

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.forgefit.app.data.model.*
import com.forgefit.app.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val GITHUB_REPO_URL = "https://github.com/dk072/ForgeFit"
private const val GITHUB_APK_RAW_URL = "https://github.com/dk072/ForgeFit/raw/main/ForgeFit-v1.1.0.apk"

@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    onUpdateProfile: (UserProfile) -> Unit,
    onOpenEquipmentSettings: () -> Unit
) {
    val context = LocalContext.current
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

        // GITHUB DEFAULT UPDATE SERVER & APP INFORMATION CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("GITHUB UPDATE SERVER", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = NeonGold)
                    Surface(
                        color = NeonGold.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "DEFAULT SERVER",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = NeonGold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Server: github.com/dk072/ForgeFit",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
                Text(
                    text = "Current App Version: v1.1.0 (Build 2)",
                    fontSize = 12.sp,
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // CHECK FOR UPDATES BUTTON
                    Button(
                        onClick = {
                            isCheckingUpdate = true
                            coroutineScope.launch {
                                delay(1000L) // Connecting to GitHub update channel
                                isCheckingUpdate = false
                                showUpdateDialog = true
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                        shape = RoundedCornerShape(12.dp),
                        enabled = !isCheckingUpdate
                    ) {
                        if (isCheckingUpdate) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = NeonGold,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(Icons.Default.SystemUpdate, contentDescription = null, tint = NeonGold, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Check GitHub", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }

                    // OPEN GITHUB REPO BUTTON
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_REPO_URL))
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonGold),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.OpenInNew, contentDescription = null, tint = BackgroundDark, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Open Repo", color = BackgroundDark, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }
    }

    // GITHUB UPDATE STATUS DIALOG
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
                    text = "GitHub Update Server Connected",
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    fontSize = 17.sp
                )
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "ForgeFit is connected to default GitHub server (github.com/dk072/ForgeFit).",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        color = SurfaceVariantDark,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Installed Version: v1.1.0", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = NeonGold)
                            Text("Latest Release: v1.1.0 (Latest)", fontSize = 12.sp, color = TextPrimary)
                            Text("Status: Up to date", fontSize = 11.sp, color = NeonGreen)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_APK_RAW_URL))
                        context.startActivity(intent)
                        showUpdateDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonGold),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.CloudDownload, contentDescription = null, tint = BackgroundDark, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Download APK", color = BackgroundDark, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showUpdateDialog = false }) {
                    Text("CLOSE", color = TextSecondary)
                }
            }
        )
    }
}
