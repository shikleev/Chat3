package com.example.chat.presentation.ui.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.example.chat.presentation.model.ui.CornersRadius
import com.example.chat.utils.dp
import kotlin.math.roundToInt


object CardDrawable {

    fun create(
        @ColorInt backgroundColors: IntArray,
        cornerRadius: CornersRadius,
        @ColorInt strokeColor: Int? = null,
        strokeWidth: Float? = null,
        isDashed: Boolean = false,
        dashWidth: Float = 6f.dp,
        dashGap: Float = 5f.dp,
        @ColorInt rippleColor: Int? = null,
    ): Drawable {
        val backgroundDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            backgroundColors
        ).apply {
            cornerRadii = createCornersArray(cornerRadius)
            if (strokeColor != null && strokeWidth != null) {
                if (isDashed) {
                    setStroke(strokeWidth.roundToInt(), strokeColor, dashWidth, dashGap)
                } else {
                    setStroke(strokeWidth.roundToInt(), strokeColor)
                }
            }
        }

        // Добавляем ripple-эффект
        val rippleDrawable = if (rippleColor != null) {
            RippleDrawable(
                ColorStateList.valueOf(rippleColor),
                backgroundDrawable,
                null
            )
        } else {
            backgroundDrawable
        }
        return rippleDrawable
    }

    private fun createCornersArray(cornersRadius: CornersRadius): FloatArray {
        return FloatArray(8).also {
            it[0] = cornersRadius.topLeft.toFloat()
            it[1] = cornersRadius.topLeft.toFloat()
            it[2] = cornersRadius.topRight.toFloat()
            it[3] = cornersRadius.topRight.toFloat()
            it[4] = cornersRadius.bottomRight.toFloat()
            it[5] = cornersRadius.bottomRight.toFloat()
            it[6] = cornersRadius.bottomLeft.toFloat()
            it[7] = cornersRadius.bottomLeft.toFloat()
        }
    }
}