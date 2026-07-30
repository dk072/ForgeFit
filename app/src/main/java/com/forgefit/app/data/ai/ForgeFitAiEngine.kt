package com.forgefit.app.data.ai

import com.forgefit.app.data.model.*

data class AiChatMessage(
    val id: String = System.currentTimeMillis().toString(),
    val sender: MessageSender,
    val text: String,
    val timestampMillis: Long = System.currentTimeMillis()
)

enum class MessageSender { USER, AI_COACH }

object ForgeFitAiEngine {

    val QUICK_PROMPTS = listOf(
        "💪 How can I build more muscle with my 5KG dumbbells?",
        "🎯 Generate an optimal 30-min Chest & Triceps routine",
        "🥗 What are my recommended daily macros for muscle gain?",
        "⚡ How do I fix shoulder pain during Dumbbell Press?",
        "🔥 Analyze my recent workout streak and volume progress"
    )

    fun generateAiResponse(
        prompt: String,
        userProfile: UserProfile,
        sessions: List<WorkoutSession>,
        exercises: List<Exercise>
    ): String {
        val lower = prompt.lowercase()

        return when {
            lower.contains("5kg") || lower.contains("fixed") || lower.contains("build more muscle") || lower.contains("weight limit") -> {
                """
                🤖 **ForgeFit AI Fixed-Dumbbell Strategy**:
                Since your dumbbells are ${userProfile.leftDumbbellWeightKg} ${userProfile.weightUnit.symbol}, maximize hyper-trophy using these 4 AI progression rules:
                
                1. **Eccentric Tempo**: Take 3–4 seconds to lower every rep.
                2. **Peak Pause**: Pause 1.5 seconds at peak muscle contraction.
                3. **Unilateral Focus**: Switch from 2-arm Floor Press to **Single-Arm Floor Press** for 35% higher core & chest activation.
                4. **Volume Overload**: Increase sets from 3 to 4 before adding reps.
                """.trimIndent()
            }

            lower.contains("routine") || lower.contains("chest") || lower.contains("triceps") || lower.contains("generate") -> {
                """
                🤖 **AI Generated 30-Min High-Tension Routine**:
                *Equipment: 2 Dumbbells + Bodyweight*
                
                1. **Dumbbell Squeeze Press**: 4 Sets × 12 Reps (60s rest)
                2. **Single-Arm Floor Press**: 3 Sets × 10 Reps per side
                3. **Decline Push-Ups**: 3 Sets × Max Controlled Reps
                4. **Overhead Dumbbell Tricep Extension**: 3 Sets × 14 Reps
                
                💡 *AI Tip: Focus on squeezing dumbbells together on Squeeze Press for maximum pectoral recruitment.*
                """.trimIndent()
            }

            lower.contains("macro") || lower.contains("diet") || lower.contains("nutrition") || lower.contains("protein") -> {
                val weight = 70f // default reference or profile based
                val proteinGrams = (weight * 2.0f).toInt()
                """
                🥗 **ForgeFit AI Nutrition Recommendations**:
                *Goal: ${userProfile.goal.displayName}*
                
                • **Daily Protein Target**: ~${proteinGrams}g - 150g per day (essential for 2-dumbbell hypertrophy).
                • **Pre-Workout Fuel**: Complex carbs 60 mins before session (Oats, Bananas, Whole Wheat).
                • **Post-Workout Recovery**: 30-40g protein within 45 mins of training.
                • **Hydration**: Minimum 3.5 Liters water daily.
                """.trimIndent()
            }

            lower.contains("shoulder") || lower.contains("pain") || lower.contains("form") || lower.contains("fix") -> {
                """
                ⚡ **AI Form & Joint Safety Audit**:
                If experiencing shoulder discomfort during pressing:
                
                1. **Elbow Angle**: Tuck elbows at a **45-degree angle** to your torso. Never flare them out at 90 degrees.
                2. **Scapular Retraction**: Pinch your shoulder blades together and press shoulders down into the floor.
                3. **Floor Protection**: Floor Press naturally prevents over-stretching the shoulder joint capsule compared to bench press.
                """.trimIndent()
            }

            lower.contains("streak") || lower.contains("volume") || lower.contains("progress") || lower.contains("analyze") -> {
                val totalVol = sessions.sumOf { it.totalVolumeKg.toDouble() }.toInt()
                """
                🔥 **ForgeFit AI Progress Audit**:
                • **Completed Sessions**: ${sessions.size} Workouts
                • **Total Training Volume**: $totalVol KG Lifted
                • **Training Streak**: ${sessions.size} Days Active
                
                🌟 **AI Verdict**: Great consistency! You are building an impressive home training baseline with ${userProfile.leftDumbbellWeightKg} ${userProfile.weightUnit.symbol} dumbbells. Keep progressive overload active!
                """.trimIndent()
            }

            else -> {
                """
                🤖 **ForgeFit AI Assistant**:
                I am your personal AI workout coach! I can help you with:
                • Custom 2-Dumbbell workout recommendations
                • Progressive overload strategies for fixed weights
                • Exercise technique & form audits
                • Recovery & nutrition macro targets
                
                What would you like to optimize today?
                """.trimIndent()
            }
        }
    }
}
