package com.mramallo.lumieretv

import android.app.Application
import com.mramallo.lumieretv.data.api.ApiService
import com.mramallo.lumieretv.data.api.RetrofitHelper
import com.mramallo.lumieretv.data.api.TmdbRepo

class MyApplication: Application() {

    lateinit var tmdbRepo: TmdbRepo

    override fun onCreate() {
        super.onCreate()

        init()
    }

    private fun init() {
        val service = RetrofitHelper.getInstance().create(ApiService::class.java)
        tmdbRepo = TmdbRepo(service)
    }

}