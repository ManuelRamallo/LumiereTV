package com.mramallo.lumieretv.presentation

import android.os.Bundle
import android.os.PersistableBundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.mramallo.lumieretv.R
import com.mramallo.lumieretv.databinding.ActivityDetailBinding

class DetailActivity: FragmentActivity() {

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

    }


}