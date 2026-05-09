package com.example.a212236_syazwana_nazatul_project1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.a212236_syazwana_nazatul_project1.ui.theme.EcoEarnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            EcoEarnTheme {
                RecyclingApp()
            }
        }
    }
}