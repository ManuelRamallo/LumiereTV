package com.mramallo.lumieretv.util

import com.bumptech.glide.Glide
import com.mramallo.lumieretv.data.Constants.URL_BASE_IMAGE_BANNER
import com.mramallo.lumieretv.data.Constants.URL_BASE_IMAGE_POSTER


fun getPosterImage(imageData: String):String {
    return URL_BASE_IMAGE_POSTER + imageData
}

fun getBannerImage(imageData: String?): String? {
    if(imageData.isNullOrEmpty()) {
        return null
    }
    return URL_BASE_IMAGE_BANNER + imageData
}