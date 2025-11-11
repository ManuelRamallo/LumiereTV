package com.mramallo.lumieretv.presentation.player

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.FragmentActivity
import com.mramallo.lumieretv.data.model.DetailResponse
import kotlin.jvm.java

class PlaybackActivity: FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("movie_detail", DetailResponse::class.java)
        } else {
            intent.getParcelableExtra<DetailResponse>("movie_detail")
        }

        val fragment = MyVideoFragment()
        val bundle = Bundle()
        bundle.putParcelable("movie_detail", data)
        fragment.arguments = bundle


        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit()
        }

    }

}