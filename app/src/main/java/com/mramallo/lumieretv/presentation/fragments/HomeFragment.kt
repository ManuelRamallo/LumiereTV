package com.mramallo.lumieretv.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mramallo.lumieretv.MyApplication
import com.mramallo.lumieretv.R
import com.mramallo.lumieretv.data.api.Response
import com.mramallo.lumieretv.data.model.Result
import com.mramallo.lumieretv.databinding.FragmentHomeBinding
import com.mramallo.lumieretv.presentation.DetailActivity
import com.mramallo.lumieretv.presentation.MainActivity
import com.mramallo.lumieretv.presentation.viewmodels.HomeViewModel
import com.mramallo.lumieretv.presentation.viewmodels.HomeViewModelFactory
import com.mramallo.lumieretv.util.getBannerImage

class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding
    private lateinit var listFragment: ListFragment

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val repository = (requireActivity().application as MyApplication).tmdbRepo
        viewModel =
            ViewModelProvider(this, HomeViewModelFactory(repository))[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    fun init(view: View) {

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Load fragment for list movies
        listFragment = ListFragment()
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.list_fragment, listFragment)
        transaction.commit()

        listFragment.setOnNavigateLeftRequest {
            (activity as? MainActivity)?.openSideMenuFromContent()
        }

        viewModel.nowPlayingMovies.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Loading -> {}
                is Response.Success -> {
                    it.data?.results.let { results ->
                        listFragment.bindData(results = results, "Now Playing")
                    }
                }

                is Response.Error -> {}
            }
        }

        viewModel.topRatedMovies.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Loading -> {}
                is Response.Success -> {
                    it.data?.results.let { results ->
                        listFragment.bindData(results = results, "Top Rated")
                    }
                }

                is Response.Error -> {}
            }
        }

        listFragment.setOnContentSelectedListener {
            updateBanner(it)
        }

        listFragment.setOnItemClickListener {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("id", it.id)
            startActivity(intent)
        }
    }

    fun updateBanner(result: Result) {
        binding.tvTitle.text = result.title
        binding.tvDescription.text = result.overview

        Glide.with(this)
            .load(getBannerImage(result.backdrop_path))
            .into(binding.imgBanner)
    }
}