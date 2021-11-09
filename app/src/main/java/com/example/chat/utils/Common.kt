package com.example.chat.utils

import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.ceil
import kotlin.math.roundToInt

val Int.dp: Int
    get() = ceil(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics)).toInt()

val Float.dp: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)

val Int.px: Int
    get() = (this / Resources.getSystem().displayMetrics.density).roundToInt()

val Float.px: Float
    get() = ceil(this / Resources.getSystem().displayMetrics.density)