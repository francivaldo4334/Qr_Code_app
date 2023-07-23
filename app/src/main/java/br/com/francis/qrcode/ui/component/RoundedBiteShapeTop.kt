package br.com.francis.qrcode.app.ui.component

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

// Classe que define a forma com recorte similar a mordida
class RoundedBiteShapeTop(private val border: Dp) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val w = (size.width)
        val h = (size.height)
        val border = with(density){
            border.toPx()
        }
        val path = Path().apply {
            moveTo(0f, 0f)
            arcTo(
                Rect(
                    offset = Offset(0f,0f),
                    size = Size(border,border)
                ),
                -180f,
                90f,
                false
            )
            lineTo(w, 0f)
            arcTo(
                Rect(
                    offset = Offset(w-(border),0f),
                    size = Size(border,border)
                ),
                -90f,
                90f,
                false
            )
            lineTo(w, h)
            arcTo(
                Rect(
                    offset = Offset(w -(border/2),h-(border/2)),
                    size = Size(border,border)
                ),
                -90f,
                -90f,
                false
            )
            lineTo(0f, h)
            arcTo(
                Rect(
                    offset = Offset(0f-(border/2),h-(border/2)),
                    size = Size(border,border)
                ),
                0f,
                -90f,
                false
            )
            lineTo(0f, 0f)
            close()
        }
        return Outline.Generic(path)
    }
}