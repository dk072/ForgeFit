package com.forgefit.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        FavoriteExerciseEntity::class,
        CustomWorkoutEntity::class,
        WorkoutSessionEntity::class,
        PersonalRecordEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ForgeFitDatabase : RoomDatabase() {
    abstract fun dao(): ForgeFitDao

    companion object {
        @Volatile
        private var INSTANCE: ForgeFitDatabase? = null

        fun getDatabase(context: Context): ForgeFitDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ForgeFitDatabase::class.java,
                    "forgefit_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
