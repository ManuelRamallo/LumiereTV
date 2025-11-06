package com.mramallo.lumieretv.presentation.player

import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.FragmentActivity

class PlaybackActivity: FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, MyVideoFragment())
                .commit()
        }

    }

}