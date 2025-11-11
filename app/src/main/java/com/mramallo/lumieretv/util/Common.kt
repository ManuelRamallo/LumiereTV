package com.mramallo.lumieretv.util

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.TextView
import com.mramallo.lumieretv.R
import com.mramallo.lumieretv.data.model.DetailResponse

fun getWidthInPercent(context: Context, percent: Int): Int {
    val width = context.resources.displayMetrics.widthPixels
    return (width * percent) / 100
}

fun getHeightInPercent(context: Context, percent: Int): Int {
    val height = context.resources.displayMetrics.heightPixels
    return (height * percent) / 100
}

fun TextView.isEllipsized(ellipsize: (isEllipsized: Boolean) -> Unit) {
    val lineCount = layout?.lineCount ?: 0
    if(lineCount > 0 ) {
        val ellipseCount = layout?.getEllipsisCount(lineCount - 1) ?: 0
        ellipsize.invoke(ellipseCount > 0)
    }
}

fun openDescriptionDialog(context: Context, title: String?, subText: String, description: String) {
    val dialog = Dialog(context, R.style.Theme_LumiereTV)
    dialog.window?.setBackgroundDrawableResource(R.color.transparent)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.dialog_description)

    dialog.findViewById<TextView>(R.id.tvTitle).text = title
    dialog.findViewById<TextView>(R.id.tvSubTitle).text = subText
    dialog.findViewById<TextView>(R.id.description).text = description

    dialog.findViewById<TextView>(R.id.closeBtn).setOnClickListener {
        dialog.dismiss()
    }

    dialog.show()
}

fun getSubtitle(response: DetailResponse?): String {
    if (response == null ) return ""

    val rating = if(response.adult) {
        "18+"
    } else {
        "13+"
    }

    val genres = response.genres.joinToString(
        prefix = " ",
        postfix = " • ",
        separator = " • "
    ) { it.name }

    val hours: Int = response.runtime / 60
    val min: Int = response.runtime % 60

    return rating + genres + hours + "h " + min + "m"
}