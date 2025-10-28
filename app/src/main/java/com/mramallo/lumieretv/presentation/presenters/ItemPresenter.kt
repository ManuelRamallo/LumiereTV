package com.mramallo.lumieretv.presentation.presenters

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mramallo.lumieretv.R
import com.mramallo.lumieretv.domain.model.Detail
import com.mramallo.lumieretv.util.getPosterImage

class ItemPresenter: Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)

        val params = view.layoutParams
        params.width = getWidthInPercent(parent.context, 12)
        params.height = getHeightInPercent(parent.context, 32)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any?) {
        val content = item as? Detail
        Log.d("image", "content: $content")

        val imageView = viewHolder.view.findViewById<ImageView>(R.id.poster_image)

        val url = getPosterImage(content?.poster_path ?: "")
        Log.d("image", "URL: $url")
        Glide.with(viewHolder.view.context)
            .load(url)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e("Glide", "Error al cargar la imagen: ${e?.message}")
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d("Glide", "Imagen cargada correctamente")
                    return false
                }
            })
            .into(imageView)

    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {}

    private fun getWidthInPercent(context: Context, percent: Int): Int {
        val width = context.resources.displayMetrics.widthPixels ?: 0
        return (width * percent) / 100
    }

    private fun getHeightInPercent(context: Context, percent: Int): Int {
        val height = context.resources.displayMetrics.heightPixels ?: 0
        return (height * percent) / 100
    }
}