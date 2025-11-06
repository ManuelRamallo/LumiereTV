package com.mramallo.lumieretv.presentation.player

import android.net.Uri
import android.os.Bundle
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.MediaPlayerAdapter
import androidx.leanback.media.PlaybackGlue
import androidx.leanback.media.PlaybackTransportControlGlue
import androidx.leanback.widget.PlaybackSeekDataProvider
import androidx.core.net.toUri

class MyVideoFragment: VideoSupportFragment() {

    private lateinit var playerGlue: PlaybackTransportControlGlue<MediaPlayerAdapter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val playerAdapter = MediaPlayerAdapter(requireActivity()).apply {
            setRepeatAction(PlaybackTransportControlGlue.ACTION_REPEAT)
        }

        playerGlue = PlaybackTransportControlGlue(requireActivity(), playerAdapter).apply {
            host = VideoSupportFragmentGlueHost(this@MyVideoFragment)
            title = "My Android TV Development"
            subtitle = "My demo Subtitle"
        }

        val uriPath = "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        playerAdapter.setDataSource(uriPath.toUri())

        playerGlue.addPlayerCallback(object : PlaybackGlue.PlayerCallback() {
            override fun onPreparedStateChanged(glue: PlaybackGlue) {
                if (glue.isPrepared) {
                    playerGlue.play()
                }
            }
        })
    }

}