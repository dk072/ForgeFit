package com.forgefit.app.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.forgefit.app.data.model.Equipment
import com.forgefit.app.data.model.UserProfile
import com.forgefit.app.ui.theme.*

@Composable
fun EquipmentSettingsScreen(
    userProfile: UserProfile,
    onUpdateProfile: (UserProfile) -> Unit,
    onBack: () -> Unit
) {
    val enabledEquipment = remember(userProfile) { mutableStateListOf(*userProfile.enabledEquipment.toTypedArray()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextPrimary)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "EQUIPMENT CONFIGURATION",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Select all equipment available in your training space",
            fontSize = 13.sp,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(20.dp))

        Equipment.values().forEach { eq ->
            val isEnabled = enabledEquipment.contains(eq)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(eq.displayName, fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 15.sp)
                        Text(
                            text = if (eq == Equipment.DUMBBELL_2 || eq == Equipment.BODYWEIGHT) "Core Requirement" else "Optional Expansion",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }

                    Checkbox(
                        checked = isEnabled,
                        onCheckedChange = { checked ->
                            if (checked) {
                                if (!enabledEquipment.contains(eq)) enabledEquipment.add(eq)
                            } else {
                                if (eq != Equipment.DUMBBELL_2 && eq != Equipment.BODYWEIGHT) {
                                    enabledEquipment.remove(eq)
                                }
                            }
                            onUpdateProfile(userProfile.copy(enabledEquipment = enabledEquipment.toList()))
                        },
                        colors = CheckboxDefaults.colors(checkedColor = NeonGold)
                    )
                }
            }
        }
    }
}
