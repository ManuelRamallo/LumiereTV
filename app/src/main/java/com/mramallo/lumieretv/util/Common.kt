package com.mramallo.lumieretv.util

import android.content.Context

fun getWidthInPercent(context: Context, percent: Int): Int {
    val width = context.resources.displayMetrics.widthPixels ?: 0
    return (width * percent) / 100
}