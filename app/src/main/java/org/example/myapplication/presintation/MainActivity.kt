package org.example.myapplication.presintation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.AndroidEntryPoint
import org.example.myapplication.R
import org.example.myapplication.presintation.fragment.BottomFragment
import org.example.myapplication.presintation.fragment.TopFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.top_frame)

        if (currentFragment == null) {
            val topFragment = TopFragment()
            val bottomFragment = BottomFragment()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.top_frame, topFragment)
                .add(R.id.bottom_frame, bottomFragment)
                .commit()

        }
    }
}