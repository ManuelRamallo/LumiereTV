package com.mramallo.lumieretv.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.mramallo.lumieretv.R
import com.mramallo.lumieretv.data.DataModel
import com.mramallo.lumieretv.data.Detail
import com.mramallo.lumieretv.databinding.FragmentHomeBinding
import com.mramallo.lumieretv.util.getBannerImage
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class HomeFragment : Fragment () {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding
    private lateinit var listFragment: ListFragment

    private var startTime = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)

        var elapsed = System.currentTimeMillis() - startTime
        Log.d("TIEMPO", "HomeFragment - Tiempo hasta que la pantalla está visible: ${elapsed} ms")
    }

    fun init(view: View) {
        startTime = System.currentTimeMillis()

        //_binding = FragmentHomeBinding.inflate(layoutInflater)
        // setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Load fragment for list movies
        listFragment = ListFragment()
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.list_fragment, listFragment)
        transaction.commit()

        val gson = Gson()
        val i: InputStream = requireContext().assets.open("movies.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList: DataModel = gson.fromJson(br, DataModel::class.java)

        listFragment.binData(dataList = dataList)

        listFragment.setOnContentSelectedListener {
            updateBanner(it)
        }
    }

    fun updateBanner(detail: Detail) {
        binding.tvTitle.text = detail.title
        binding.tvDescription.text = detail.overview

        Glide.with(this)
            .load(getBannerImage(detail.backdrop_path))
            .into(binding.imgBanner)
    }
}