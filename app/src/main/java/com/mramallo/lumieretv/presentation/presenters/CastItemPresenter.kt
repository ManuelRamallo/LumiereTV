package com.mramallo.lumieretv.presentation.presenters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mramallo.lumieretv.R
import com.mramallo.lumieretv.data.model.Cast
import com.mramallo.lumieretv.util.getHeightInPercent
import com.mramallo.lumieretv.util.getWidthInPercent

class CastItemPresenter: Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cast_item_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        item: Any?
    ) {
        val content = item as Cast

        val imageView = viewHolder.view.findViewById<ImageView>(R.id.cast_img)

        val path = "https://www.themoviedb.org/t/p/w780" + content.profile_path
        Glide.with(viewHolder.view.context)
            .load(path)
            .apply (RequestOptions.circleCropTransform())
            .into(imageView)

    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {}
}