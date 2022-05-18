package com.boos.stock.ui.details

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boos.stock.domain.model.IntradayInfoModel
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun StockChart(
    infoList: List<IntradayInfoModel> = emptyList(),
    modifier: Modifier = Modifier,
    graphColor: Color = Color.Green
) {
    val spacing = 100f
    val transparentGraphColor = remember {
        graphColor.copy(0.5f)
    }
    val upperYCloseValue = remember(infoList) {
        (infoList.maxOfOrNull { it.close }?.plus(1)?.roundToInt()) ?: 0
    }
    val lowerYCloseValue = remember(infoList) {
        (infoList.minOfOrNull { it.close }?.toInt()) ?: 0
    }

    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Canvas(modifier = modifier) {
        val spacePerHour = (size.width - spacing) / infoList.size
        (0 until infoList.size - 1 step 2).forEach { i ->
            val info = infoList[i]
            val hour = info.date.hour
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    hour.toString(),
                    spacing + i * spacePerHour,
                    size.height - 5,
                    textPaint
                )
            }
        }

        val yClosePriceStep = (upperYCloseValue - lowerYCloseValue) / 5f
        (0..4).forEach { i ->
            val price = round(lowerYCloseValue + yClosePriceStep * i).toString()
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    price,
                    30f,
                    size.height - spacing - i * size.height / 5f,
                    textPaint
                )
            }
        }

        var lastXValue = 0f
        val strokePath = Path().apply {
            val height = size.height
            for (i in infoList.indices) {
                val info = infoList[i]
                val nextInfo = infoList.getOrNull(i + 1) ?: infoList.last()
                val currentPriceRatio =
                    (info.close - lowerYCloseValue) / (upperYCloseValue - lowerYCloseValue)
                val nextPriceRatio =
                    (nextInfo.close - lowerYCloseValue) / (upperYCloseValue - lowerYCloseValue)

                val currentX = spacing + i * spacePerHour
                val currentY = height - spacing - (currentPriceRatio * height).toFloat()
                val nextX = spacing + (i + 1) * spacePerHour
                val nextY = height - spacing - (nextPriceRatio * height).toFloat()
                if (i == 0) {
                    moveTo(currentX, currentY)
                }
                lastXValue = (currentX + nextX) / 2f
                quadraticBezierTo(
                    currentX, currentY, lastXValue, (currentY + nextY) / 2f
                )
            }
        }

        val fillPath = android.graphics.Path(strokePath.asAndroidPath()).asComposePath().apply {
            lineTo(lastXValue, size.height - spacing)
            lineTo(spacing, size.height - spacing)
            close()
        }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(transparentGraphColor, Color.Transparent),
                endY = size.height - spacing
            )
        )
        drawPath(
            path = strokePath,
            color = graphColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Butt
            )
        )
    }
}