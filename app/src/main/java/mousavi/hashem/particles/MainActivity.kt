package mousavi.hashem.particles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import mousavi.hashem.particles.ui.theme.ParticlesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParticlesTheme {
                MainScreen()
            }
        }
    }
}
