package org.example.myapplication.data.repository

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.google.android.material.color.DynamicColors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.example.myapplication.R
import javax.inject.Inject
import javax.inject.Singleton

data class UserPreferences(
    val isDarkModeEnabled: Boolean,
    val isDynamicColorsEnabled: Boolean,
    val color: Int
)

@Singleton
class PreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    private val DYNAMIC_COLORS_KEY = booleanPreferencesKey("dyn_colors")
    private val COLOR_KEY = intPreferencesKey("color")

    private suspend fun<T> savePreference(
        prefKey: Preferences.Key<T>,
        value: T
    ) {
        dataStore.edit { pr ->
            pr[prefKey] = value
        }
    }

    private fun<T> getValue(key: Preferences.Key<T>, defaultValue: T) = dataStore.data.map { pr ->
        pr[key] ?: defaultValue
    }

    suspend fun saveDarkMode(value: Boolean) = savePreference(DARK_MODE_KEY, value)
    suspend fun saveDynamicColor(value: Boolean) = savePreference(DYNAMIC_COLORS_KEY, value)
    suspend fun saveColor(value: Int) = savePreference(COLOR_KEY, value)

    fun getDarkMode() = getValue(DARK_MODE_KEY, false)
    fun getDynamicColors() = getValue(DYNAMIC_COLORS_KEY, DynamicColors.isDynamicColorAvailable())
    fun getColor() = getValue(COLOR_KEY, R.color.md_theme_primary)

    fun getPreferences(): Flow<UserPreferences> {
        return dataStore.data.map { prefs ->
            UserPreferences(
                isDarkModeEnabled = prefs[DARK_MODE_KEY] == true,
                isDynamicColorsEnabled =  prefs[DYNAMIC_COLORS_KEY] == true,
                color = prefs[COLOR_KEY] ?: R.color.md_theme_primary
            )
        }
    }
}