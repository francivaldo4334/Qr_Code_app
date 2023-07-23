package br.com.francis.qrcode.app.ui.component

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DottedLine(modifier:Modifier = Modifier.fillMaxWidth(),stroke: Dp = 8.dp) {
    Canvas(modifier = modifier) {
        val path = PathEffect.dashPathEffect(floatArrayOf(25f,25f,0f))
        drawLine(
            color = Color.Black,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = with(density){
                stroke.toPx()
            },
            pathEffect = path,
            cap = StrokeCap.Round
        )
    }
}