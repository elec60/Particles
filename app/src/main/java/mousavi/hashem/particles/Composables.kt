package mousavi.hashem.particles

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.pow
import kotlin.random.Random

@Composable
fun MainScreen(
    numberOfParticles: Int = 400,
    maxDistance: Float = 200f
) {
    BoxWithConstraints {
        val height = this.maxHeight
        val width = this.maxWidth
        val density = LocalDensity.current
        val heightInPx = with(density) { height.roundToPx() }
        val widthInPx = with(density) { width.roundToPx() }
        val particles = remember {
            List(numberOfParticles) {
                val radius = (10..40).random().toFloat()
                val x = (radius.toInt()..(widthInPx - radius).toInt()).random().toFloat()
                val y = (radius.toInt()..(heightInPx - radius).toInt()).random().toFloat()
                Particle(
                    x = x,
                    y = y,
                    vx = Random.nextInt(1, 6).toFloat(),
                    vy = Random.nextInt(1, 6).toFloat(),
                    radius = radius,
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Red,
                            Color.Yellow,
                            Color.Green,
                            Color.Blue,
                            Color.Magenta
                        )
                    )
                )
            }
        }

        var trigger by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            while (true) {
                withFrameNanos {
                    println(it)
                    trigger = !trigger
                }
            }
        }

        Canvas(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
        ) {
            particles.forEachIndexed { index, particle ->
                if (trigger) {
                    val x = particle.x + particle.vx
                    val y = particle.y + particle.vy
                    if (x > widthInPx - particle.radius || x < particle.radius) {
                        particle.vx *= -1
                    }
                    if (y > heightInPx - particle.radius || y < particle.radius) {
                        particle.vy *= -1
                    }
                    particle.x = x
                    particle.y = y
                }

                for (i in (index until particles.size)) {
                    val otherParticle = particles[i]
                    val distance = kotlin.math.sqrt(
                        (particle.x - otherParticle.x).pow(2) +
                                (particle.y - otherParticle.y).pow(2)
                    )
                    if (distance < maxDistance) {
                        val alpha = 1 - distance / maxDistance
                        drawLine(
                            color = Color.White.copy(alpha = alpha),
                            start = Offset(
                                x = particle.x,
                                y = particle.y
                            ),
                            end = Offset(
                                x = otherParticle.x,
                                y = otherParticle.y
                            ),
                            strokeWidth = 4f
                        )
                    }
                }


                drawCircle(
                    brush = particle.brush,
                    radius = particle.radius,
                    center = Offset(
                        x = particle.x,
                        y = particle.y
                    )
                )
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}


