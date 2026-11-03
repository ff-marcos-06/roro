package com.example.rodriguez

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.rodriguez.ui.NutriCalApp
import com.example.rodriguez.ui.theme.NutriCalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutriCalTheme {
                NutriCalApp()
            }
        }
    }
}
