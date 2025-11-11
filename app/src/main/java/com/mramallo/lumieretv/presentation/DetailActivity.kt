package com.mramallo.lumieretv.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mramallo.lumieretv.MyApplication
import com.mramallo.lumieretv.R
import com.mramallo.lumieretv.data.api.Response
import com.mramallo.lumieretv.data.model.DetailResponse
import com.mramallo.lumieretv.databinding.ActivityDetailBinding
import com.mramallo.lumieretv.domain.model.Detail
import com.mramallo.lumieretv.presentation.fragments.ListFragment
import com.mramallo.lumieretv.presentation.player.PlaybackActivity
import com.mramallo.lumieretv.util.getSubtitle
import com.mramallo.lumieretv.util.isEllipsized
import com.mramallo.lumieretv.util.openDescriptionDialog
import kotlin.jvm.java

class DetailActivity: FragmentActivity() {

    lateinit var binding: ActivityDetailBinding
    lateinit var viewModel: DetailViewModel
    val castFragment = ListFragment()

    var detailResponse: DetailResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        addFragment(castFragment = castFragment)

        val movieId = intent.getIntExtra("id", 0)
        val repository = (application as MyApplication).tmdbRepo

        viewModel = ViewModelProvider(this, DetailViewModelFactory(repository, movieId))[DetailViewModel::class.java]

        viewModel.movieDetails.observe(this) {
            when(it) {
                is Response.Loading -> {
                }
                is Response.Success -> {
                    detailResponse = it.data
                    setData(it.data)
                }
                is Response.Error -> {
                }
            }
        }

        viewModel.castDetails.observe(this) {
            when(it) {
                is Response.Loading -> {
                }
                is Response.Success -> {
                   it.data?.let { castResponse ->
                       if(castResponse.cast.isNotEmpty()) {
                           castFragment.bindCastData(castResponse.cast)
                       }
                   }
                }
                is Response.Error -> {
                }
            }
        }

        binding.addToMylist.setOnKeyListener { view, keyCode, keyEvent ->
            when(keyCode) {
                KeyEvent.KEYCODE_DPAD_DOWN -> {
                    if(keyEvent.action == KeyEvent.ACTION_DOWN) {
                        castFragment.requestFocus()
                    }
                }
            }

            false
        }

        binding.play.setOnClickListener {
            val intent = Intent(this, PlaybackActivity::class.java)
            intent.putExtra("movie_detail", detailResponse)
            startActivity(intent)
        }
    }

    private fun addFragment(castFragment: ListFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.cast_fragment, castFragment)
        transaction.commit()
    }

    private fun setData(response: DetailResponse?) {
        Log.d("DATA", "setData ->> $response")
        binding.title.text = response?.title ?: ""
        binding.subtitle.text = getSubtitle(response)
        binding.description.text = response?.overview ?: ""

        val path = "https://www.themoviedb.org/t/p/w780" + (response?.backdrop_path ?: "")
        Glide.with(this)
            .load(path)
            .into(binding.imgBanner)

        binding.description.isEllipsized {isEllipsized ->
            binding.showMore.visibility = if(isEllipsized) View.VISIBLE else View.GONE

            binding.showMore.setOnClickListener {
                openDescriptionDialog(
                    this,
                    response?.title,
                    getSubtitle(response),
                    response?.overview.toString()
                )
            }

        }

    }
}