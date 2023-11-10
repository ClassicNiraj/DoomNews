package com.example.doomnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.doomnews.ui.DoomNewsApp
import com.example.doomnews.ui.theme.DoomNewsTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoomNewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val windowSize = calculateWindowSizeClass(this)
                    DoomNewsApp(
                        windowSize = windowSize.widthSizeClass
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoomNewsAppCompactPreview() {
    DoomNewsTheme {
        Surface {
            DoomNewsApp(windowSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview(name = "Medium Size Screen", widthDp = 480)
@Composable
fun DoomNewsAppMediumPreview() {
    DoomNewsTheme {
        Surface {
            DoomNewsApp(windowSize = WindowWidthSizeClass.Medium)
        }
    }
}

@Preview(name = "Expanded Size Screen", widthDp = 720)
@Composable
fun DoomNewsAppExpandedPreview() {
    DoomNewsTheme {
        Surface {
            DoomNewsApp(windowSize = WindowWidthSizeClass.Expanded)
        }
    }
}
// TODO: Add previews for different screen sizes