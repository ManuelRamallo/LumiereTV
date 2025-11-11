package com.mramallo.lumieretv.presentation.player

import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.MediaPlayerAdapter
import androidx.leanback.media.PlaybackGlue
import androidx.leanback.media.PlaybackTransportControlGlue
import androidx.leanback.widget.PlaybackSeekDataProvider
import androidx.core.net.toUri
import androidx.core.view.isVisible
import com.mramallo.lumieretv.R

class MyVideoFragment: VideoSupportFragment() {

    private lateinit var transportGlue: CustomTransportControlGlue

    private lateinit var playerGlue: PlaybackTransportControlGlue<MediaPlayerAdapter>

    private lateinit var fastForwardIndicatorView: View
    private lateinit var rewindIndicatorView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val playerAdapter = MediaPlayerAdapter(requireActivity()).apply {
            setRepeatAction(PlaybackTransportControlGlue.ACTION_REPEAT)
        }

        /*playerGlue = PlaybackTransportControlGlue(requireActivity(), playerAdapter).apply {
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
        })*/

        transportGlue = CustomTransportControlGlue(
            context = requireContext(),
            playerAdapter = BasicMediaPlayerAdapter(requireContext())
        )

        transportGlue.host = VideoSupportFragmentGlueHost(this)
        transportGlue.subtitle = "My demo subtitle"
        transportGlue.title = "My Android TV Development"
        val uriPath = "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        transportGlue.playerAdapter.setDataSource(Uri.parse(uriPath))

        setOnKeyInterceptListener { view,  keyCode, event ->
            if( isControlsOverlayVisible || event.repeatCount > 0) {
                isShowOrHideControlsOverlayOnUserInteraction  = true
            } else when(keyCode) {
                KeyEvent.KEYCODE_DPAD_RIGHT -> {
                    isShowOrHideControlsOverlayOnUserInteraction = event.action != KeyEvent.ACTION_DOWN
                    if(event.action == KeyEvent.ACTION_DOWN) {
                        animateIndicator(fastForwardIndicatorView)
                    }
                }
                KeyEvent.KEYCODE_DPAD_LEFT -> {
                    isShowOrHideControlsOverlayOnUserInteraction = event.action != KeyEvent.ACTION_DOWN
                    if(event.action == KeyEvent.ACTION_DOWN) {
                        animateIndicator(rewindIndicatorView)
                    }
                }
            }
            transportGlue.onKey(view, keyCode, event)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) as ViewGroup

        fastForwardIndicatorView = inflater.inflate(R.layout.view_forward, view, false)
        view.addView(fastForwardIndicatorView)

        rewindIndicatorView = inflater.inflate(R.layout.view_rewind, view, false)
        view.addView(rewindIndicatorView)


        return view
    }

    private fun animateIndicator(indicator: View) {
        indicator.animate()
            .withEndAction {
                indicator.isVisible = false
                indicator.alpha = 1F
                indicator.scaleX = 1F
                indicator.scaleY = 1F
            }
            .withStartAction {
                indicator.isVisible = true
            }
            .alpha(0.2F)
            .scaleX(2F)
            .scaleY(2F)
            .setDuration(400)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }


}