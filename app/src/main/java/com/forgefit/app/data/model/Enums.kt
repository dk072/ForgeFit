package com.forgefit.app.data.model

enum class MuscleGroup(val displayName: String) {
    CHEST("Chest"),
    BACK("Back"),
    SHOULDERS("Shoulders"),
    BICEPS("Biceps"),
    TRICEPS("Triceps"),
    FOREARMS("Forearms"),
    ABS("Abs / Core"),
    QUADRICEPS("Quadriceps"),
    HAMSTRINGS("Hamstrings"),
    GLUTES("Glutes"),
    CALVES("Calves")
}

enum class Difficulty(val displayName: String) {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced")
}

enum class Equipment(val displayName: String) {
    DUMBBELL_2("2 Dumbbells"),
    DUMBBELL_1("1 Dumbbell"),
    BODYWEIGHT("Bodyweight"),
    BENCH("Bench (Optional)"),
    PULLUP_BAR("Pull-up Bar (Optional)")
}

enum class Goal(val displayName: String) {
    BUILD_MUSCLE("Build Muscle"),
    GAIN_STRENGTH("Gain Strength"),
    FAT_LOSS("Fat Loss"),
    BODY_RECOMP("Body Recomposition"),
    GENERAL_FITNESS("General Fitness")
}

enum class ExperienceLevel(val displayName: String) {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced")
}

enum class MovementType(val displayName: String) {
    PUSH("Push"),
    PULL("Pull"),
    SQUAT("Squat"),
    HINGE("Hinge"),
    LUNGE("Lunge"),
    ISOLATION("Isolation"),
    CORE("Core")
}

enum class BodyPosition(val displayName: String) {
    FLOOR("Floor / Mat"),
    STANDING("Standing"),
    SEATED("Seated (Chair)"),
    BENT_OVER("Bent-Over"),
    KNEELING("Kneeling")
}

enum class WeightUnit(val symbol: String) {
    KG("KG"),
    LB("LB")
}

enum class DumbbellType(val displayName: String) {
    FIXED("Fixed Weight"),
    ADJUSTABLE("Adjustable")
}
