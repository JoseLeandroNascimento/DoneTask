package com.joseleandro.donetask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.joseleandro.donetask.ui.App
import com.joseleandro.donetask.ui.theme.DoneTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DoneTaskTheme(
                dynamicColor = false
            ) {
                App()
            }
        }
    }
}
