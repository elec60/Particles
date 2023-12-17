package mousavi.hashem.particles

import androidx.compose.ui.graphics.Brush

data class Particle(
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    val radius: Float,
    val brush: Brush
)
