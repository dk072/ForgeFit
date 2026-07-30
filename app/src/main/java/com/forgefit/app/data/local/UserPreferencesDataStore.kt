package com.forgefit.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.forgefit.app.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesDataStore(private val context: Context) {

    private object Keys {
        val EXPERIENCE_LEVEL = stringPreferencesKey("experience_level")
        val GOAL = stringPreferencesKey("goal")
        val WORKOUT_DAYS = intPreferencesKey("workout_days")
        val DURATION = intPreferencesKey("duration")
        val DUMBBELL_TYPE = stringPreferencesKey("dumbbell_type")
        val LEFT_WEIGHT = floatPreferencesKey("left_weight")
        val RIGHT_WEIGHT = floatPreferencesKey("right_weight")
        val WEIGHT_UNIT = stringPreferencesKey("weight_unit")
        val IS_ONBOARDED = booleanPreferencesKey("is_onboarded")
        val DEFAULT_REST_TIME = intPreferencesKey("default_rest_time")
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        val VIBRATION_ENABLED = booleanPreferencesKey("vibration_enabled")
        val ENABLED_EQUIPMENT = stringSetPreferencesKey("enabled_equipment")
    }

    val userProfileFlow: Flow<UserProfile> = context.dataStore.data.map { prefs ->
        UserProfile(
            experienceLevel = try { ExperienceLevel.valueOf(prefs[Keys.EXPERIENCE_LEVEL] ?: "BEGINNER") } catch (e: Exception) { ExperienceLevel.BEGINNER },
            goal = try { Goal.valueOf(prefs[Keys.GOAL] ?: "BUILD_MUSCLE") } catch (e: Exception) { Goal.BUILD_MUSCLE },
            workoutDaysPerWeek = prefs[Keys.WORKOUT_DAYS] ?: 3,
            targetDurationMinutes = prefs[Keys.DURATION] ?: 45,
            dumbbellType = try { DumbbellType.valueOf(prefs[Keys.DUMBBELL_TYPE] ?: "FIXED") } catch (e: Exception) { DumbbellType.FIXED },
            leftDumbbellWeightKg = prefs[Keys.LEFT_WEIGHT] ?: 5.0f,
            rightDumbbellWeightKg = prefs[Keys.RIGHT_WEIGHT] ?: 5.0f,
            weightUnit = try { WeightUnit.valueOf(prefs[Keys.WEIGHT_UNIT] ?: "KG") } catch (e: Exception) { WeightUnit.KG },
            enabledEquipment = (prefs[Keys.ENABLED_EQUIPMENT] ?: setOf("DUMBBELL_2", "DUMBBELL_1", "BODYWEIGHT")).mapNotNull {
                try { Equipment.valueOf(it) } catch (e: Exception) { null }
            },
            isOnboarded = prefs[Keys.IS_ONBOARDED] ?: false,
            defaultRestTimeSeconds = prefs[Keys.DEFAULT_REST_TIME] ?: 60,
            soundEnabled = prefs[Keys.SOUND_ENABLED] ?: true,
            vibrationEnabled = prefs[Keys.VIBRATION_ENABLED] ?: true
        )
    }

    suspend fun updateUserProfile(profile: UserProfile) {
        context.dataStore.edit { prefs ->
            prefs[Keys.EXPERIENCE_LEVEL] = profile.experienceLevel.name
            prefs[Keys.GOAL] = profile.goal.name
            prefs[Keys.WORKOUT_DAYS] = profile.workoutDaysPerWeek
            prefs[Keys.DURATION] = profile.targetDurationMinutes
            prefs[Keys.DUMBBELL_TYPE] = profile.dumbbellType.name
            prefs[Keys.LEFT_WEIGHT] = profile.leftDumbbellWeightKg
            prefs[Keys.RIGHT_WEIGHT] = profile.rightDumbbellWeightKg
            prefs[Keys.WEIGHT_UNIT] = profile.weightUnit.name
            prefs[Keys.IS_ONBOARDED] = profile.isOnboarded
            prefs[Keys.DEFAULT_REST_TIME] = profile.defaultRestTimeSeconds
            prefs[Keys.SOUND_ENABLED] = profile.soundEnabled
            prefs[Keys.VIBRATION_ENABLED] = profile.vibrationEnabled
            prefs[Keys.ENABLED_EQUIPMENT] = profile.enabledEquipment.map { it.name }.toSet()
        }
    }

    suspend fun setOnboarded(onboarded: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.IS_ONBOARDED] = onboarded
        }
    }
}
