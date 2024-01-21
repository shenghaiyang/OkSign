package com.shenghaiyang.oksign

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.shenghaiyang.oksign.ui.App
import com.shenghaiyang.oksign.ui.theme.OkSignTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            OkSignTheme {
                App()
            }
        }

    }

}


