package org.example.myapplication

import android.app.Application
import android.os.Build
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.example.myapplication.data.store.UserPreferences

@HiltAndroidApp
class ThemeApp: Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        applicationScope.launch {
            UserPreferences.getDynamicColors(applicationContext).collect {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && it) {

                }
            }
        }
    }
}