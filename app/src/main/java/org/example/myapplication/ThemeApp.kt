package org.example.myapplication

import android.app.Application
import android.os.Build
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.example.myapplication.data.repository.PreferencesRepository
import org.example.myapplication.data.store.UserPreferences
import javax.inject.Inject

@HiltAndroidApp
class ThemeApp: Application() {

    @Inject
    lateinit var repository: PreferencesRepository

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        applicationScope.launch {
            val isDynamicColorsEnabled = repository.getPreferences().first().isDynamicColorsEnabled
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && isDynamicColorsEnabled) {
                DynamicColors.applyToActivitiesIfAvailable(this@ThemeApp)
            } else {
                setTheme(R.style.AppTheme)
            }
        }
    }
}