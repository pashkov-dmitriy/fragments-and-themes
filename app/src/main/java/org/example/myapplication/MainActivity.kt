package org.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.example.myapplication.presintation.fragment.TopFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.top_frame)

        if (currentFragment == null) {
            val fragment = TopFragment()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.top_frame, fragment)
                .commit()

        }
    }
}