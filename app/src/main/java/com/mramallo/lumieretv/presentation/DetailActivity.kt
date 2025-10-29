package com.mramallo.lumieretv.presentation

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mramallo.lumieretv.MyApplication
import com.mramallo.lumieretv.R
import com.mramallo.lumieretv.data.api.Response
import com.mramallo.lumieretv.data.model.DetailResponse
import com.mramallo.lumieretv.databinding.ActivityDetailBinding
import com.mramallo.lumieretv.presentation.fragments.ListFragment

class DetailActivity: FragmentActivity() {

    lateinit var binding: ActivityDetailBinding
    lateinit var viewModel: DetailViewModel
    val castFragment = ListFragment()

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
    }



    private fun addFragment(castFragment: ListFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.cast_fragment, castFragment)
        transaction.commit()
    }

    private fun setData(it: DetailResponse?) {
        Log.d("DATA", "setData ->> $it")
        binding.title.text = it?.title ?: ""
        binding.subtitle.text = getSubtitle(it)
        binding.description.text = it?.overview ?: ""

        val path = "https://www.themoviedb.org/t/p/w780" + (it?.backdrop_path ?: "")
        Glide.with(this)
            .load(path)
            .into(binding.imgBanner)

    }

    fun getSubtitle(response: DetailResponse?): String {
        val rating = if(response!!.adult) {
            "18+"
        } else {
            "13+"
        }

        val genres = response.genres.joinToString(
            prefix = " ",
            postfix = " • ",
            separator = " • "
        ) { it.name }

        val hours: Int = response.runtime / 60
        val min: Int = response.runtime % 60

        return rating + genres + hours + "h " + min + "m"
    }

}