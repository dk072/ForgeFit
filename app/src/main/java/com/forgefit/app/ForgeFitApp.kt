package com.forgefit.app

import android.app.Application
import com.forgefit.app.data.local.ForgeFitDatabase
import com.forgefit.app.data.local.UserPreferencesDataStore
import com.forgefit.app.data.repository.ForgeFitRepository

class ForgeFitApp : Application() {
    lateinit var repository: ForgeFitRepository
        private set

    override fun onCreate() {
        super.onCreate()
        val db = ForgeFitDatabase.getDatabase(this)
        val dataStore = UserPreferencesDataStore(this)
        repository = ForgeFitRepository(db.dao(), dataStore)
    }
}
