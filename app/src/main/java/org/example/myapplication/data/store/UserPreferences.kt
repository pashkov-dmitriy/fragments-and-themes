package org.example.myapplication.data.store

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.material.color.DynamicColors
import kotlinx.coroutines.flow.map
import org.example.myapplication.R

object UserPreferences {
    private val Context.dataStore by preferencesDataStore(name = "user_prefs")

    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    private val DYNAMIC_COLORS_KEY = booleanPreferencesKey("dyn_colors")
    private val COLOR_KEY = intPreferencesKey("color")

     private suspend fun<T> savePreference(
        context: Context,
        prefKey: Preferences.Key<T>,
        value: T
    ) {
        context.dataStore.edit { pr ->
            pr[prefKey] = value
        }
    }

    suspend fun saveDarkMode(context: Context, value: Boolean) = savePreference(context, DARK_MODE_KEY, value)
    suspend fun saveDynamicColor(context: Context, value: Boolean) = savePreference(context, DYNAMIC_COLORS_KEY, value)
    suspend fun saveColor(context: Context, value: Int) = savePreference(context, COLOR_KEY, value)

    private fun<T> getValue(context: Context, key: Preferences.Key<T>, defaultValue: T) = context.dataStore.data.map { pr ->
        pr[key] ?: defaultValue
    }

    fun getDarkMode(context: Context) = getValue(context, DARK_MODE_KEY, false)
    fun getDynamicColors(context: Context) = getValue(context, DYNAMIC_COLORS_KEY, DynamicColors.isDynamicColorAvailable())
    fun getColor(context: Context) = getValue(context, COLOR_KEY, ResourcesCompat.getColor(context.resources, R.color.md_theme_primary, context.theme))
}