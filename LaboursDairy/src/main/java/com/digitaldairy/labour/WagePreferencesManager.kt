package com.digitaldairy.labour

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "wage_prefs")

class WagePreferencesManager(private val context: Context) {

    // Define keys
    companion object {
        private val DAILY_WAGE_KEY = floatPreferencesKey("daily_wage")
        private val HOURS_WORKED_KEY = intPreferencesKey("hours_worked")
    }

    // Save daily wage
    suspend fun saveDailyWage(wage: Float) {
        context.dataStore.edit { preferences ->
            preferences[DAILY_WAGE_KEY] = wage
        }
    }

    // Save hours worked
    suspend fun saveHoursWorked(hours: Int) {
        context.dataStore.edit { preferences ->
            preferences[HOURS_WORKED_KEY] = hours
        }
    }

    // Read daily wage
    val dailyWageFlow: Flow<Float> = context.dataStore.data
        .map { preferences -> preferences[DAILY_WAGE_KEY] ?: 0f }

    // Read hours worked
    val hoursWorkedFlow: Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[HOURS_WORKED_KEY] ?: 0 }
}