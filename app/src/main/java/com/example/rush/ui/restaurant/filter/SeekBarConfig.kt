package com.example.rush.ui.restaurant.filter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar

class NumberedSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatSeekBar(context, attrs, defStyleAttr) {

    private val paint = Paint()

    init {
        paint.color = resources.getColor(android.R.color.black, resources.newTheme()) // Color del texto
        paint.textSize = 35f // Tamaño del texto
        paint.textAlign = Paint.Align.CENTER // Alineación del texto
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val steps = max
        val width = width - paddingLeft - paddingRight
        val stepSize = width.toFloat() / steps.toFloat()

        for (i in 0..steps) {
            val x = stepSize * i + paddingLeft
            val y = ((height - paddingBottom) - 10).toFloat()

            canvas?.drawText((i + 1).toString(), x, y, paint) // Dibuja el texto debajo de cada punto
        }
    }
}