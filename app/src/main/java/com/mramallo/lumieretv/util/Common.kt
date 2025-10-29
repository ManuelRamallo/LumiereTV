package com.mramallo.lumieretv.util

import android.content.Context

fun getWidthInPercent(context: Context, percent: Int): Int {
    val width = context.resources.displayMetrics.widthPixels
    return (width * percent) / 100
}

fun getHeightInPercent(context: Context, percent: Int): Int {
    val height = context.resources.displayMetrics.heightPixels
    return (height * percent) / 100
}