package com.forgefit.app.data.seed

import com.forgefit.app.data.model.*

object ExerciseSeedData {

    val ALL_EXERCISES = listOf(
        // CHEST
        Exercise(
            id = "db_floor_press",
            name = "Dumbbell Floor Press",
            description = "A powerful compound pressing movement targeting the chest, shoulders, and triceps safely from the floor without needing a bench.",
            primaryMuscle = MuscleGroup.CHEST,
            secondaryMuscles = listOf(MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS),
            equipmentRequired = Equipment.DUMBBELL_2,
            difficulty = Difficulty.BEGINNER,
            movementType = MovementType.PUSH,
            bodyPosition = BodyPosition.FLOOR,
            defaultSets = 3,
            minReps = 8,
            maxReps = 12,
            restTimeSeconds = 75,
            instructions = listOf(
                "Lie flat on your back on the floor with your knees bent and feet flat.",
                "Hold a dumbbell in each hand at chest height with palms facing forward and elbows at a 45-degree angle to your torso.",
                "Press the dumbbells upward until your arms are extended above your chest.",
                "Lower the weights slowly until your upper arms gently touch the floor.",
                "Pause briefly at the bottom before pressing back up."
            ),
            formTips = listOf(
                "Keep your lower back naturally arched and upper back firmly pressed against the floor.",
                "Do not slam your elbows into the ground at the bottom.",
                "Exhale as you press up, inhale on the way down."
            ),
            commonMistakes = listOf(
                "Flaring elbows straight out at 90 degrees.",
                "Bouncing elbows off the floor."
            ),
            safetyNotes = "Lower upper arms with control to protect shoulder joint capsular tissue.",
            animationKey = "anim_floor_press"
        ),
        Exercise(
            id = "single_arm_floor_press",
            name = "Single-Arm Floor Press",
            description = "Unilateral chest pressing exercise that builds core anti-rotation strength and fixes side-to-side muscle imbalances.",
            primaryMuscle = MuscleGroup.CHEST,
            secondaryMuscles = listOf(MuscleGroup.TRICEPS, MuscleGroup.ABS),
            equipmentRequired = Equipment.DUMBBELL_1,
            difficulty = Difficulty.INTERMEDIATE,
            movementType = MovementType.PUSH,
            bodyPosition = BodyPosition.FLOOR,
            defaultSets = 3,
            minReps = 10,
            maxReps = 15,
            restTimeSeconds = 60,
            instructions = listOf(
                "Lie on your back with knees bent and hold one dumbbell with a overhand grip at chest height.",
                "Brace your core to prevent your body from rotating off the ground.",
                "Press the single dumbbell vertically over your shoulder.",
                "Lower under control until upper arm touches the ground."
            ),
            formTips = listOf(
                "Press your non-working shoulder and hip down into the floor.",
                "Maintain strict torso position throughout the set."
            ),
            commonMistakes = listOf(
                "Twisting your shoulders off the floor during the lift."
            ),
            safetyNotes = "Use your free hand to guide the dumbbell when starting heavy sets.",
            animationKey = "anim_single_arm_floor_press"
        ),
        Exercise(
            id = "db_squeeze_press",
            name = "Dumbbell Squeeze Press",
            description = "Chest exercise performed by pressing two dumbbells tightly together throughout the entire movement for maximum pectoral tension.",
            primaryMuscle = MuscleGroup.CHEST,
            secondaryMuscles = listOf(MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS),
            equipmentRequired = Equipment.DUMBBELL_2,
            difficulty = Difficulty.BEGINNER,
            movementType = MovementType.PUSH,
            bodyPosition = BodyPosition.FLOOR,
            defaultSets = 3,
            minReps = 10,
            maxReps = 14,
            restTimeSeconds = 60,
            instructions = listOf(
                "Lie back on the floor with dumbbells resting on your chest.",
                "Press the flat sides of the dumbbells firmly together over your sternum.",
                "Maintaining constant inward pressure, press dumbbells straight up.",
                "Lower back down slowly while continuous squeezing."
            ),
            formTips = listOf(
                "Squeeze as hard as possible inward; weight used matters less than contraction tension."
            ),
            commonMistakes = listOf(
                "Allowing the dumbbells to separate during the rep."
            ),
            safetyNotes = "Maintain a secure grip so dumbbells do not slip.",
            animationKey = "anim_squeeze_press"
        ),
        Exercise(
            id = "db_floor_fly",
            name = "Dumbbell Floor Fly",
            description = "Isolation exercise targeting the chest muscles with a wide arc motion while protected by the floor.",
            primaryMuscle = MuscleGroup.CHEST,
            secondaryMuscles = listOf(MuscleGroup.SHOULDERS),
            equipmentRequired = Equipment.DUMBBELL_2,
            difficulty = Difficulty.INTERMEDIATE,
            movementType = MovementType.ISOLATION,
            bodyPosition = BodyPosition.FLOOR,
            defaultSets = 3,
            minReps = 10,
            maxReps = 15,
            restTimeSeconds = 60,
            instructions = listOf(
                "Lie flat on the floor with dumbbells held straight up over your chest, palms facing each other.",
                "Maintain a slight bend in your elbows throughout the movement.",
                "Lower arms outward in a wide arc until triceps touch the floor.",
                "Squeeze chest muscles to bring dumbbells back together."
            ),
            formTips = listOf(
                "Keep elbow angle fixed like hugging a large barrel."
            ),
            commonMistakes = listOf(
                "Bending elbows excessively and turning it into a press."
            ),
            safetyNotes = "The floor prevents over-stretching the anterior shoulder capsule.",
            animationKey = "anim_floor_fly"
        ),
        Exercise(
            id = "push_up",
            name = "Standard Push-Up",
            description = "Classic bodyweight upper body exercise for chest, front shoulders, and core stability.",
            primaryMuscle = MuscleGroup.CHEST,
            secondaryMuscles = listOf(MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS, MuscleGroup.ABS),
            equipmentRequired = Equipment.BODYWEIGHT,
            difficulty = Difficulty.BEGINNER,
            movementType = MovementType.PUSH,
            bodyPosition = BodyPosition.FLOOR,
            defaultSets = 3,
            minReps = 10,
            maxReps = 20,
            restTimeSeconds = 60,
            instructions = listOf(
                "Set hands slightly wider than shoulder-width on the floor.",
                "Form a straight line from your head to your heels.",
                "Lower your chest down until it is an inch from the floor.",
                "Push firmly through your palms back to full extension."
            ),
            formTips = listOf(
                "Keep glutes and abs engaged to protect your lower back from sagging."
            ),
            commonMistakes = listOf(
                "Sagging hips or lifting glutes in the air."
            ),
            safetyNotes = "Keep neck aligned with spine.",
            animationKey = "anim_pushup"
        ),
        Exercise(
            id = "decline_push_up",
            name = "Decline Push-Up",
            description = "Advanced push-up variation with feet elevated on a chair or couch to target upper chest.",
            primaryMuscle = MuscleGroup.CHEST,
            secondaryMuscles = listOf(MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
            equipmentRequired = Equipment.BODYWEIGHT,
            difficulty = Difficulty.ADVANCED,
            movementType = MovementType.PUSH,
            bodyPosition = BodyPosition.FLOOR,
            defaultSets = 3,
            minReps = 8,
            maxReps = 12,
            restTimeSeconds = 75,
            instructions = listOf(
                "Place your feet on a safe household chair or couch edge.",
                "Place hands on the floor in plank position.",
                "Lower chest towards floor under control.",
                "Press forcefully back to starting position."
            ),
            formTips = listOf(
                "Focus on squeezing the upper chest at peak height."
            ),
            commonMistakes = listOf(
                "Hyperextending spine."
            ),
            safetyNotes = "Ensure chair is stable and will not slip.",
            animationKey = "anim_decline_pushup"
        ),

        // BACK
        Exercise(
            id = "db_bent_over_row",
            name = "Dumbbell Bent-Over Row",
            description = "Fundamental compound pulling exercise for middle back, latissimus dorsi, and rear deltoids.",
            primaryMuscle = MuscleGroup.BACK,
            secondaryMuscles = listOf(MuscleGroup.BICEPS, MuscleGroup.SHOULDERS, MuscleGroup.FOREARMS),
            equipmentRequired = Equipment.DUMBBELL_2,
            difficulty = Difficulty.BEGINNER,
            movementType = MovementType.PULL,
            bodyPosition = BodyPosition.BENT_OVER,
            defaultSets = 4,
            minReps = 8,
            maxReps = 12,
            restTimeSeconds = 75,
            instructions = listOf(
                "Stand with feet shoulder-width apart, holding a dumbbell in each hand.",
                "Hinge forward at hips keeping flat back until torso is almost parallel to floor.",
                "Pull dumbbells upward towards your ribcage, driving elbows toward the ceiling.",
                "Squeeze shoulder blades together at top, then lower smoothly."
            ),
            formTips = listOf(
                "Keep spine neutral; do not round your lower back."
            ),
            commonMistakes = listOf(
                "Using momentum to swing weights up."
            ),
            safetyNotes = "Brace core strongly to protect lumbar spine.",
            animationKey = "anim_bent_over_row"
        ),
        Exercise(
            id = "one_arm_db_row",
            name = "One-Arm Dumbbell Row",
            description = "Supported single-arm row providing high lat isolation and maximum range of motion using a sturdy chair.",
            primaryMuscle = MuscleGroup.BACK,
            secondaryMuscles = listOf(MuscleGroup.BICEPS, MuscleGroup.FOREARMS),
            equipmentRequired = Equipment.DUMBBELL_1,
            difficulty = Difficulty.BEGINNER,
            movementType = MovementType.PULL,
            bodyPosition = BodyPosition.BENT_OVER,
            defaultSets = 3,
            minReps = 10,
            maxReps = 15,
            restTimeSeconds = 60,
            instructions = listOf(
                "Place left knee and left hand on a sturdy chair for support.",
                "Hold dumbbell in right hand with arm hanging straight down.",
                "Pull dumbbell up towards hip socket, squeezing right lat.",
                "Lower with full arm stretch at bottom."
            ),
            formTips = listOf(
                "Pull with your elbow, not your biceps."
            ),
            commonMistakes = listOf(
                "Twisting torso excessively at the top."
            ),
            safetyNotes = "Keep supporting hand anchored securely.",
            animationKey = "anim_one_arm_row"
        ),
        Exercise(
            id = "db_pullover",
            name = "Dumbbell Floor Pullover",
            description = "Unique movement working the lats, serratus, and chest while lying on floor.",
            primaryMuscle = MuscleGroup.BACK,
            secondaryMuscles = listOf(MuscleGroup.CHEST, MuscleGroup.TRICEPS),
            equipmentRequired = Equipment.DUMBBELL_1,
            difficulty = Difficulty.INTERMEDIATE,
            movementType = MovementType.PULL,
            bodyPosition = BodyPosition.FLOOR,
            defaultSets = 3,
            minReps = 10,
            maxReps = 14,
            restTimeSeconds = 60,
            instructions = listOf(
                "Lie flat on floor holding one dumbbell overhead with both hands forming a diamond grip under upper inner plate.",
                "Slightly bend elbows and lower weight back behind your head toward floor.",
                "Feel stretch in lats, then pull dumbbell back up above chest."
            ),
            formTips = listOf(
                "Keep lower back in contact with floor by bracing core."
            ),
            commonMistakes = listOf(
                "Bending elbows into a tricep extension."
            ),
            safetyNotes = "Avoid dropping dumbbell behind head; move slowly.",
            animationKey = "anim_db_pullover"
        ),
        Exercise(
            id = "db_rdl",
            name = "Dumbbell Romanian Deadlift",
            description = "Posterior chain builder targeting hamstrings, glutes, and lower/upper back stability.",
            primaryMuscle = MuscleGroup.HAMSTRINGS,
            secondaryMuscles = listOf(MuscleGroup.GLUTES, MuscleGroup.BACK, MuscleGroup.FOREARMS),
            equipmentRequired = Equipment.DUMBBELL_2,
            difficulty = Difficulty.INTERMEDIATE,
            movementType = MovementType.HINGE,
            bodyPosition = BodyPosition.STANDING,
            defaultSets = 3,
            minReps = 8,
            maxReps = 12,
            restTimeSeconds = 90,
            instructions = listOf(
                "Stand tall holding dumbbells in front of thighs, feet hip-width.",
                "Push hips back and hinge forward with slight knee bend.",
                "Lower weights along shins until deep hamstring stretch.",
                "Drive hips forward to return to standing position."
            ),
            formTips = listOf(
                "Keep dumbbells close to legs throughout movement."
            ),
            commonMistakes = listOf(
                "Rounding spine at bottom."
            ),
            safetyNotes = "Stop descent once hamstring flexibility limit is reached.",
            animationKey = "anim_db_rdl"
        ),

        // SHOULDERS
        Exercise(
            id = "db_shoulder_press",
            name = "Dumbbell Shoulder Press",
            description = "Primary vertical pressing movement for building thick anterior and lateral deltoids.",
            primaryMuscle = MuscleGroup.SHOULDERS,
            secondaryMuscles = listOf(MuscleGroup.TRICEPS),
            equipmentRequired = Equipment.DUMBBELL_2,
            difficulty = Difficulty.BEGINNER,
            movementType = MovementType.PUSH,
            bodyPosition = BodyPosition.STANDING,
            defaultSets = 3,
            minReps = 8,
            maxReps = 12,
            restTimeSeconds = 75,
            instructions = listOf(
                "Stand shoulder-width apart or sit upright on a chair.",
                "Hold dumbbells at shoulder height with palms facing forward.",
                "Press weights overhead until arms are lockout extended.",
                "Lower smoothly back to ear height."
            ),
            formTips = listOf(
                "Brace abs to avoid excessive arching in lower back."
            ),
            commonMistakes = listOf(
                "Leaning backward excessively."
            ),
            safetyNotes = "Do not lock out elbows aggressively at top.",
            animationKey = "anim_shoulder_press"
        ),
        Exercise(
            id = "arnold_press",
            name = "Arnold Press",
            description = "Rotational overhead press designed by Arnold Schwarzenegger to engage all three deltoid heads.",
            primaryMuscle = MuscleGroup.SHOULDERS,
            secondaryMuscles = listOf(MuscleGroup.TRICEPS),
            equipmentRequired = Equipment.DUMBBELL_2,
            difficulty = Difficulty.INTERMEDIATE,
            movementType = MovementType.PUSH,
            bodyPosition = BodyPosition.STANDING,
            defaultSets = 3,
            minReps = 10,
            maxReps = 14,
            restTimeSeconds = 60,
            instructions = listOf(
                "Hold dumbbells in front of chest with palms facing you (supinated).",
                "As you press upward, rotate wrists outward so palms face forward at top.",
                "Reverse rotation slowly on descent."
            ),
            formTips = listOf(
                "Smooth, controlled rotation throughout path."
            ),
            commonMistakes = listOf(
                "Rushing rotation at start of press."
            ),
            safetyNotes = "Start lighter than standard shoulder press.",
            animationKey = "anim_arnold_press"
        ),
        Exercise(
            id = "lateral_raise",
            name = "Dumbbell Lateral Raise",
            description = "The premier isolation exercise for building side delts and creating a wider V-taper physique.",
            primaryMuscle = MuscleGroup.SHOULDERS,
            secondaryMuscles = listOf(MuscleGroup.FOREARMS),
            equipmentRequired = Equipment.DUMBBELL_2,
            difficulty = Difficulty.BEGINNER,
            movementType = MovementType.ISOLATION,
            bodyPosition = BodyPosition.STANDING,
            defaultSets = 3,
            minReps = 12,
            maxReps = 16,
            restTimeSeconds = 60,
            instructions = listOf(
                "Stand holding dumbbells at sides with palms facing inward.",
                "Raise arms out to sides with slight bend in elbows until parallel to floor.",
                "Pause briefly at peak height.",
                "Lower under control over 2-3 seconds."
            ),
            formTips = listOf(
                "Lead with elbows; imagine pouring water from pitchers at top."
            ),
            commonMistakes = listOf(
                "Shrugging shoulders up toward ears during lift."
            ),
            safetyNotes = "Avoid swinging torso.",
            animationKey = "anim_lateral_raise"
        ),
        Exercise(
            id = "rear_delt_fly",
            name = "Bent-Over Rear Delt Fly",
            description = "Target posterior deltoids and upper back muscles crucial for healthy posture.",
            primaryMuscle = MuscleGroup.SHOULDERS,
            secondaryMuscles = listOf(MuscleGroup.BACK),
            equipmentRequired = Equipment.DUMBBELL_2,
            difficulty = Difficulty.INTERMEDIATE,
            movementType = MovementType.ISOLATION,
            bodyPosition = BodyPosition.BENT_OVER,
            defaultSets = 3,
            minReps = 12,
            maxReps = 15,
            restTimeSeconds = 60,
            instructions = listOf(
                "Hinge forward at hips until torso is parallel to floor.",
                "Let dumbbells hang down with palms facing each other.",
                "Raise arms out to sides by squeezing back of shoulders.",
                "Lower with control."
            ),
            formTips = listOf(
                "Focus on rear delts rather than squeezing middle traps."
            ),
            commonMistakes = listOf(
                "Using momentum from lower back."
            ),
            safetyNotes = "Keep head in neutral alignment.",
            animationKey = "anim_rear_delt_fly"
        ),

        // BICEPS
        Exercise(
            id = "db_curl",
            name = "Standing Dumbbell Curl",
            description = "Classic biceps builder focusing on full supination and peak arm flex.",
            primaryMuscle = MuscleGroup.BICEPS,
            secondaryMuscles = listOf(MuscleGroup.FOREARMS),
            equipmentRequired = Equipment.DUMBBELL_2,
            difficulty = Difficulty.BEGINNER,
            movementType = MovementType.ISOLATION,
            bodyPosition = BodyPosition.STANDING,
            defaultSets = 3,
            minReps = 10,
            maxReps = 14,
            restTimeSeconds = 60,
            instructions = listOf(
                "Stand tall with dumbbells by sides, palms facing forward.",
                "Keep upper arms pinned to your torso.",
                "Curl weights upward while squeezing biceps.",
                "Lower down under strict tension."
            ),
            formTips = listOf(
                "Avoid swinging hips to bounce weight up."
            ),
            commonMistakes = listOf(
                "Moving elbows forward off body."
            ),
            safetyNotes = "Lower fully to stretch biceps safely.",
            animationKey = "anim_db_curl"
        ),
        Exercise(
            id = "hammer_curl",
            name = "Dumbbell Hammer Curl",
            description = "Neutral grip curl variation targeting brachialis and brachioradialis for overall arm thickness.",
            primaryMuscle = MuscleGroup.BICEPS,
            secondaryMuscles = listOf(MuscleGroup.FOREARMS),
            equipmentRequired = Equipment.DUMBBELL_2,
            difficulty = Difficulty.BEGINNER,
            movementType = MovementType.ISOLATION,
            bodyPosition = BodyPosition.STANDING,
            defaultSets = 3,
            minReps = 10,
            maxReps = 12,
            restTimeSeconds = 60,
            instructions = listOf(
                "Hold dumbbells with palms facing each other (neutral grip).",
                "Curl weights toward shoulders without rotating wrists.",
                "Squeeze firmly at peak height and lower."
            ),
            formTips = listOf(
                "Keep wrist stiff and neutral throughout rep."
            ),
            commonMistakes = listOf(
                "Using body momentum."
            ),
            safetyNotes = "Great arm exercise for reducing elbow joint strain.",
            animationKey = "anim_hammer_curl"
        ),
        Exercise(
            id = "concentration_curl",
            name = "Seated Concentration Curl",
            description = "Strict seated biceps isolation using inner thigh support to eliminate all momentum.",
            primaryMuscle = MuscleGroup.BICEPS,
            secondaryMuscles = listOf(MuscleGroup.FOREARMS),
            equipmentRequired = Equipment.DUMBBELL_1,
            difficulty = Difficulty.INTERMEDIATE,
            movementType = MovementType.ISOLATION,
            bodyPosition = BodyPosition.SEATED,
            defaultSets = 3,
            minReps = 10,
            maxReps = 15,
            restTimeSeconds = 60,
            instructions = listOf(
                "Sit on a sturdy chair, legs spread wide.",
                "Rest back of tricep against inner thigh.",
                "Curl dumbbell toward shoulder, squeezing biceps hard.",
                "Lower smoothly back down."
            ),
            formTips = listOf(
                "Keep upper arm stationary against leg."
            ),
            commonMistakes = listOf(
                "Pulling back with shoulder."
            ),
            safetyNotes = "Full control on descent.",
            animationKey = "anim_concentration_curl"
        ),
        Exercise(
            id = "zottman_curl",
            name = "Zottman Curl",
            description = "Dual-purpose curl that trains biceps on concentric and forearms on eccentric phase.",
            primaryMuscle = MuscleGroup.BICEPS,
            secondaryMuscles = listOf(MuscleGroup.FOREARMS),
            equipmentRequired = Equipment.DUMBBELL_2,
            difficulty = Difficulty.ADVANCED,
            movementType = MovementType.ISOLATION,
            bodyPosition = BodyPosition.STANDING,
            defaultSets = 3,
            minReps = 8,
            maxReps = 12,
            restTimeSeconds = 60,
            instructions = listOf(
                "Curl dumbbells with palms up.",
                "At top of movement, rotate wrists so palms face down.",
                "Lower dumbbells slowly with palms-down grip.",
                "Rotate palms back up at bottom."
            ),
            formTips = listOf(
                "Take a full 3 seconds to lower the weight."
            ),
            commonMistakes = listOf(
                "Dropping weight fast on descent."
            ),
            safetyNotes = "Use moderate weight due to forearm focus.",
            animationKey = "anim_zottman_curl"
        ),

        // TRICEPS
        Exercise(
            id = "db_overhead_tricep_ext",
            name = "Dumbbell Overhead Tricep Extension",
            description = "Long head triceps builder using two hands on a single dumbbell overhead.",
            primaryMuscle = MuscleGroup.TRICEPS,
            secondaryMuscles = listOf(MuscleGroup.SHOULDERS),
            equipmentRequired = Equipment.DUMBBELL_1,
            difficulty = Difficulty.BEGINNER,
            movementType = MovementType.ISOLATION,
            bodyPosition = BodyPosition.STANDING,
            defaultSets = 3,
            minReps = 10,
            maxReps = 14,
            restTimeSeconds = 60,
            instructions = listOf(
                "Hold one dumbbell with both hands, hands cupping top inner weight plate.",
                "Press dumbbell directly overhead.",
                "Lower weight behind head by bending elbows while keeping upper arms close to head.",
                "Extend elbows back to starting overhead position."
            ),
            formTips = listOf(
                "Keep elbows pointed forward rather than flaring out."
            ),
            commonMistakes = listOf(
                "Arching back excessively."
            ),
            safetyNotes = "Maintain secure palm contact with weight plate.",
            animationKey = "anim_overhead_ext"
        ),
        Exercise(
            id = "db_tricep_kickback",
            name = "Dumbbell Tricep Kickback",
            description = "Target triceps peak extension contraction in bent-over position.",
            primaryMuscle = MuscleGroup.TRICEPS,
            secondaryMuscles = listOf(MuscleGroup.FOREARMS),
            equipmentRequired = Equipment.DUMBBELL_1,
            difficulty = Difficulty.BEGINNER,
            movementType = MovementType.ISOLATION,
            bodyPosition = BodyPosition.BENT_OVER,
            defaultSets = 3,
            minReps = 12,
            maxReps = 15,
            restTimeSeconds = 60,
            instructions = listOf(
                "Support torso with one knee and hand on chair.",
                "Hold dumbbell in other hand with upper arm parallel to torso.",
                "Extend elbow backward until arm is completely straight.",
                "Squeeze triceps at top, then return to 90 degrees."
            ),
            formTips = listOf(
                "Keep upper arm still; only forearm moves."
            ),
            commonMistakes = listOf(
                "Dropping upper arm toward floor."
            ),
            safetyNotes = "Focus on lock-out squeeze rather than heavy load.",
            animationKey = "anim_tricep_kickback"
        ),
        Exercise(
            id = "close_grip_floor_press",
            name = "Close-Grip Dumbbell Floor Press",
            description = "Heavy compound pressing variation shifting load directly to triceps.",
            primaryMuscle = MuscleGroup.TRICEPS,
            secondaryMuscles = listOf(MuscleGroup.CHEST, MuscleGroup.SHOULDERS),
            equipmentRequired = Equipment.DUMBBELL_2,
            difficulty = Difficulty.INTERMEDIATE,
            movementType = MovementType.PUSH,
            bodyPosition = BodyPosition.FLOOR,
            defaultSets = 3,
            minReps = 8,
            maxReps = 12,
            restTimeSeconds = 75,
            instructions = listOf(
                "Lie on floor holding dumbbells touching each other over chest.",
                "Keep elbows close into sides of torso.",
                "Press straight up to lockout, keeping dumbbells touching.",
                "Lower until upper arms touch ground."
            ),
            formTips = listOf(
                "Keep elbows tight against ribs."
            ),
            commonMistakes = listOf(
                "Flaring elbows outward."
            ),
            safetyNotes = "Protect wrist joints by keeping wrists stack over elbows.",
            animationKey = "anim_close_grip_press"
        ),

        // LEGS
        Exercise(
            id = "goblet_squat",
            name = "Dumbbell Goblet Squat",
            description = "Essential quad and leg compound exercise holding one dumbbell vertically at chest level.",
            primaryMuscle = MuscleGroup.QUADRICEPS,
            secondaryMuscles = listOf(MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS, MuscleGroup.ABS),
            equipmentRequired = Equipment.DUMBBELL_1,
            difficulty = Difficulty.BEGINNER,
            movementType = MovementType.SQUAT,
            bodyPosition = BodyPosition.STANDING,
            defaultSets = 4,
            minReps = 10,
            maxReps = 15,
            restTimeSeconds = 90,
            instructions = listOf(
                "Hold dumbbell vertically against chest with palms cupping top weight head.",
                "Stand feet shoulder-width, toes angled slightly outward.",
                "Lower hips back and down into deep squat position.",
                "Drive through heels back to full extension."
            ),
            formTips = listOf(
                "Keep chest upright and knees tracking over toes."
            ),
            commonMistakes = listOf(
                "Knees caving inward or chest collapsing forward."
            ),
            safetyNotes = "Maintain flat foot contact throughout.",
            animationKey = "anim_goblet_squat"
        ),
        Exercise(
            id = "db_lunge",
            name = "Dumbbell Forward Lunges",
            description = "Unilateral lower body builder developing balance, quads, and glutes.",
            primaryMuscle = MuscleGroup.QUADRICEPS,
            secondaryMuscles = listOf(MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS, MuscleGroup.CALVES),
            equipmentRequired = Equipment.DUMBBELL_2,
            difficulty = Difficulty.INTERMEDIATE,
            movementType = MovementType.LUNGE,
            bodyPosition = BodyPosition.STANDING,
            defaultSets = 3,
            minReps = 10,
            maxReps = 12,
            restTimeSeconds = 75,
            instructions = listOf(
                "Stand holding dumbbells by sides.",
                "Step forward with right foot and lower back knee toward floor.",
                "Push back off front heel to starting stance.",
                "Alternate legs with each rep."
            ),
            formTips = listOf(
                "Maintain upright torso position."
            ),
            commonMistakes = listOf(
                "Front knee passing far beyond front toe line."
            ),
            safetyNotes = "Do not bang back knee into floor.",
            animationKey = "anim_db_lunge"
        ),
        Exercise(
            id = "bulgarian_split_squat",
            name = "Bulgarian Split Squat",
            description = "High-intensity unilateral quad and glute builder using a chair to elevate back leg.",
            primaryMuscle = MuscleGroup.QUADRICEPS,
            secondaryMuscles = listOf(MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS),
            equipmentRequired = Equipment.DUMBBELL_2,
            difficulty = Difficulty.ADVANCED,
            movementType = MovementType.SQUAT,
            bodyPosition = BodyPosition.STANDING,
            defaultSets = 3,
            minReps = 8,
            maxReps = 12,
            restTimeSeconds = 90,
            instructions = listOf(
                "Stand a couple feet in front of a chair with rear foot top resting on chair seat.",
                "Hold dumbbells at sides.",
                "Lower front thigh parallel to ground.",
                "Press through front heel back to top."
            ),
            formTips = listOf(
                "Keep 80% of weight over front foot."
            ),
            commonMistakes = listOf(
                "Standing too close to chair causing knee strain."
            ),
            safetyNotes = "Ensure elevated rear foot platform is stable.",
            animationKey = "anim_bulgarian_squat"
        ),
        Exercise(
            id = "db_glute_bridge",
            name = "Dumbbell Glute Bridge",
            description = "Targeted glute isolation exercise performed safely on floor.",
            primaryMuscle = MuscleGroup.GLUTES,
            secondaryMuscles = listOf(MuscleGroup.HAMSTRINGS, MuscleGroup.ABS),
            equipmentRequired = Equipment.DUMBBELL_1,
            difficulty = Difficulty.BEGINNER,
            movementType = MovementType.HINGE,
            bodyPosition = BodyPosition.FLOOR,
            defaultSets = 3,
            minReps = 12,
            maxReps = 16,
            restTimeSeconds = 60,
            instructions = listOf(
                "Lie on back with knees bent and feet flat on floor.",
                "Place dumbbell across hips, holding it with both hands.",
                "Drive through heels to lift hips until body forms straight line from knees to shoulders.",
                "Squeeze glutes at top before lowering."
            ),
            formTips = listOf(
                "Squeeze glutes hard at peak for 1-2 seconds."
            ),
            commonMistakes = listOf(
                "Overarching lower back."
            ),
            safetyNotes = "Keep weight secure over pelvis.",
            animationKey = "anim_glute_bridge"
        ),
        Exercise(
            id = "db_calf_raise",
            name = "Dumbbell Standing Calf Raise",
            description = "Isolation exercise for gastrocnemius muscle building lower leg strength.",
            primaryMuscle = MuscleGroup.CALVES,
            secondaryMuscles = listOf(MuscleGroup.FOREARMS),
            equipmentRequired = Equipment.DUMBBELL_2,
            difficulty = Difficulty.BEGINNER,
            movementType = MovementType.ISOLATION,
            bodyPosition = BodyPosition.STANDING,
            defaultSets = 4,
            minReps = 15,
            maxReps = 20,
            restTimeSeconds = 45,
            instructions = listOf(
                "Stand holding dumbbells at sides.",
                "Rise up onto toes as high as possible.",
                "Pause at top contraction.",
                "Lower heels back to floor slowly."
            ),
            formTips = listOf(
                "Perform rep smoothly without bouncing."
            ),
            commonMistakes = listOf(
                "Rushing through partial range of motion."
            ),
            safetyNotes = "Focus on ankle stability.",
            animationKey = "anim_calf_raise"
        ),

        // ABS & CORE
        Exercise(
            id = "weighted_crunch",
            name = "Weighted Dumbbell Crunch",
            description = "Abdominal flexor movement using a single dumbbell held across upper chest.",
            primaryMuscle = MuscleGroup.ABS,
            secondaryMuscles = emptyList(),
            equipmentRequired = Equipment.DUMBBELL_1,
            difficulty = Difficulty.BEGINNER,
            movementType = MovementType.CORE,
            bodyPosition = BodyPosition.FLOOR,
            defaultSets = 3,
            minReps = 12,
            maxReps = 15,
            restTimeSeconds = 45,
            instructions = listOf(
                "Lie on back with knees bent, holding dumbbell on upper chest.",
                "Contract abs to lift shoulders off floor.",
                "Squeeze core at peak and lower down."
            ),
            formTips = listOf(
                "Exhale forcefully on crunch upward."
            ),
            commonMistakes = listOf(
                "Pulling on neck."
            ),
            safetyNotes = "Do not jerk lower back off floor.",
            animationKey = "anim_weighted_crunch"
        ),
        Exercise(
            id = "db_russian_twist",
            name = "Dumbbell Russian Twist",
            description = "Rotational core builder strengthening obliques and deep abdominal muscles.",
            primaryMuscle = MuscleGroup.ABS,
            secondaryMuscles = listOf(MuscleGroup.FOREARMS),
            equipmentRequired = Equipment.DUMBBELL_1,
            difficulty = Difficulty.INTERMEDIATE,
            movementType = MovementType.CORE,
            bodyPosition = BodyPosition.FLOOR,
            defaultSets = 3,
            minReps = 16,
            maxReps = 24,
            restTimeSeconds = 45,
            instructions = listOf(
                "Sit on floor, lean back 45 degrees, feet elevated slightly.",
                "Hold dumbbell with both hands in front of chest.",
                "Rotate torso to right side bringing dumbbell near floor, then rotate to left side.",
                "Maintain core stability throughout."
            ),
            formTips = listOf(
                "Rotate shoulders, not just hands."
            ),
            commonMistakes = listOf(
                "Rounding lumbar spine."
            ),
            safetyNotes = "Keep rotation controlled.",
            animationKey = "anim_russian_twist"
        ),
        Exercise(
            id = "plank",
            name = "Bodyweight Forearm Plank",
            description = "Isometric core stability exercise strengthening abdominal wall and lower back endurance.",
            primaryMuscle = MuscleGroup.ABS,
            secondaryMuscles = listOf(MuscleGroup.SHOULDERS, MuscleGroup.GLUTES),
            equipmentRequired = Equipment.BODYWEIGHT,
            difficulty = Difficulty.BEGINNER,
            movementType = MovementType.CORE,
            bodyPosition = BodyPosition.FLOOR,
            defaultSets = 3,
            minReps = 30,
            maxReps = 60, // Reps represent seconds
            restTimeSeconds = 45,
            instructions = listOf(
                "Place forearms on floor with elbows directly under shoulders.",
                "Extend legs back, creating rigid line from head to heels.",
                "Contract abs, glutes, and quad muscles tightly.",
                "Hold position without letting hips sag."
            ),
            formTips = listOf(
                "Keep gaze on hands to maintain neutral neck."
            ),
            commonMistakes = listOf(
                "Piking hips in air or sagging waist down."
            ),
            safetyNotes = "Breathe rhythmically.",
            animationKey = "anim_plank"
        ),
        Exercise(
            id = "mountain_climbers",
            name = "Mountain Climbers",
            description = "Dynamic high-tempo core and cardio exercise.",
            primaryMuscle = MuscleGroup.ABS,
            secondaryMuscles = listOf(MuscleGroup.SHOULDERS, MuscleGroup.QUADRICEPS),
            equipmentRequired = Equipment.BODYWEIGHT,
            difficulty = Difficulty.INTERMEDIATE,
            movementType = MovementType.CORE,
            bodyPosition = BodyPosition.FLOOR,
            defaultSets = 3,
            minReps = 20,
            maxReps = 30,
            restTimeSeconds = 45,
            instructions = listOf(
                "Start in push-up plank position.",
                "Drive right knee towards chest, then switch quickly to left knee.",
                "Continue alternating knees in rapid controlled motion."
            ),
            formTips = listOf(
                "Keep shoulders stacked directly over hands."
            ),
            commonMistakes = listOf(
                "Bouncing hips up high."
            ),
            safetyNotes = "Maintain wrist alignment.",
            animationKey = "anim_mountain_climbers"
        ),

        // FOREARMS
        Exercise(
            id = "db_wrist_curl",
            name = "Dumbbell Seated Wrist Curl",
            description = "Forearm flexor isolation exercise building grip strength.",
            primaryMuscle = MuscleGroup.FOREARMS,
            secondaryMuscles = emptyList(),
            equipmentRequired = Equipment.DUMBBELL_2,
            difficulty = Difficulty.BEGINNER,
            movementType = MovementType.ISOLATION,
            bodyPosition = BodyPosition.SEATED,
            defaultSets = 3,
            minReps = 15,
            maxReps = 20,
            restTimeSeconds = 45,
            instructions = listOf(
                "Sit on chair resting forearms on thighs with palms facing up.",
                "Let dumbbells roll down to fingertips.",
                "Curl wrists upward to flex forearms.",
                "Lower under control."
            ),
            formTips = listOf(
                "Only wrists move; forearms stay glued to thighs."
            ),
            commonMistakes = listOf(
                "Lifting forearms off legs."
            ),
            safetyNotes = "Use smooth motion to avoid strain.",
            animationKey = "anim_wrist_curl"
        ),
        Exercise(
            id = "farmer_hold",
            name = "Dumbbell Farmer Hold",
            description = "Isometric grip and forearm endurance exercise holding heavy dumbbells.",
            primaryMuscle = MuscleGroup.FOREARMS,
            secondaryMuscles = listOf(MuscleGroup.SHOULDERS, MuscleGroup.ABS),
            equipmentRequired = Equipment.DUMBBELL_2,
            difficulty = Difficulty.BEGINNER,
            movementType = MovementType.ISOLATION,
            bodyPosition = BodyPosition.STANDING,
            defaultSets = 3,
            minReps = 30,
            maxReps = 60, // Seconds hold
            restTimeSeconds = 60,
            instructions = listOf(
                "Stand tall holding dumbbells tightly at sides.",
                "Pull shoulders back and contract core.",
                "Hold static position for target time."
            ),
            formTips = listOf(
                "Crush the handles with maximum grip force."
            ),
            commonMistakes = listOf(
                "Slouching shoulders forward."
            ),
            safetyNotes = "Set weights down safely when grip fades.",
            animationKey = "anim_farmer_hold"
        )
    )
}
