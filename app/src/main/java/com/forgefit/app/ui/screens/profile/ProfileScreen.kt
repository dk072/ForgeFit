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
import com.forgefit.app.data.updater.UpdateInfo
import com.forgefit.app.data.updater.UpdateManager
import com.forgefit.app.ui.theme.*
import kotlinx.coroutines.launch
import java.io.File

private const val GITHUB_REPO_URL = "https://github.com/dk072/ForgeFit"

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
    var isDownloading by remember { mutableStateOf(false) }
    var downloadProgress by remember { mutableStateOf(0) }
    var availableUpdateInfo by remember { mutableStateOf<UpdateInfo?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var readyApkFile by remember { mutableStateOf<File?>(null) }
    var statusMessage by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val currentVersionName = remember { UpdateManager.getCurrentVersionName(context) }

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

        // GITHUB AUTOMATIC IN-APP UPDATE ENGINE CARD
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
                    Text("AUTOMATIC GITHUB UPDATER", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = NeonGold)
                    Surface(
                        color = NeonGold.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "LIVE GITHUB ENGINE",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = NeonGold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Current App Version: v$currentVersionName",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Auto-Sync Server: raw.githubusercontent.com/dk072/ForgeFit",
                    fontSize = 11.sp,
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
                            statusMessage = "Connecting to GitHub update server..."
                            coroutineScope.launch {
                                val info = UpdateManager.checkForUpdates(context)
                                isCheckingUpdate = false
                                if (info != null) {
                                    availableUpdateInfo = info
                                    showDialog = true
                                } else {
                                    availableUpdateInfo = null
                                    showDialog = true
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                        shape = RoundedCornerShape(12.dp),
                        enabled = !isCheckingUpdate && !isDownloading
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
                            Text("Check for Update", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
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

    // UPDATE STATUS DIALOG
    if (showDialog) {
        val update = availableUpdateInfo
        AlertDialog(
            onDismissRequest = { if (!isDownloading) showDialog = false },
            containerColor = SurfaceDark,
            shape = RoundedCornerShape(20.dp),
            icon = {
                Icon(
                    imageVector = if (update != null) Icons.Default.CloudDownload else Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = if (update != null) NeonGold else NeonGreen,
                    modifier = Modifier.size(40.dp)
                )
            },
            title = {
                Text(
                    text = if (update != null) "Update Available (v${update.versionName})!" else "App is Up to Date",
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    fontSize = 17.sp
                )
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (update != null) {
                        Text(
                            text = "A new version of ForgeFit is available on GitHub!",
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
                                Text("Version: v${update.versionName}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = NeonGold)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Release Notes:\n${update.releaseNotes}", fontSize = 11.sp, color = TextPrimary, lineHeight = 16.sp)
                            }
                        }

                        if (isDownloading) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Downloading update from GitHub... $downloadProgress%", fontSize = 12.sp, color = NeonGold, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(6.dp))
                            LinearProgressIndicator(
                                progress = downloadProgress / 100f,
                                color = NeonGold,
                                trackColor = SurfaceVariantDark,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    } else {
                        Text(
                            text = "You are already using the latest version (v$currentVersionName). No update required!",
                            fontSize = 13.sp,
                            color = TextSecondary
                        )
                    }
                }
            },
            confirmButton = {
                if (update != null) {
                    Button(
                        onClick = {
                            if (readyApkFile != null) {
                                UpdateManager.installApk(context, readyApkFile!!)
                            } else {
                                isDownloading = true
                                coroutineScope.launch {
                                    val downloadedFile = UpdateManager.downloadApk(context, update.apkUrl) { progress ->
                                        downloadProgress = progress
                                    }
                                    isDownloading = false
                                    if (downloadedFile != null) {
                                        readyApkFile = downloadedFile
                                        UpdateManager.installApk(context, downloadedFile)
                                    }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NeonGold),
                        shape = RoundedCornerShape(10.dp),
                        enabled = !isDownloading
                    ) {
                        Icon(Icons.Default.CloudDownload, contentDescription = null, tint = BackgroundDark, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (readyApkFile != null) "INSTALL NOW" else "DOWNLOAD & INSTALL", color = BackgroundDark, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Button(
                        onClick = { showDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = NeonGold),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("OK", color = BackgroundDark, fontWeight = FontWeight.Bold)
                    }
                }
            },
            dismissButton = {
                if (update != null && !isDownloading) {
                    TextButton(onClick = { showDialog = false }) {
                        Text("CANCEL", color = TextSecondary)
                    }
                }
            }
        )
    }
}
