package com.mramallo.lumieretv.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.mramallo.lumieretv.R
import com.mramallo.lumieretv.data.DataModel
import com.mramallo.lumieretv.databinding.ActivityMainBinding
import com.mramallo.lumieretv.util.getBannerImage
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listFragment: ListFragment

    private var startTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        startTime = System.currentTimeMillis()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Load fragment for list movies
        listFragment = ListFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.list_fragment, listFragment)
        transaction.commit()

        val gson = Gson()
        val i: InputStream = this.assets.open("movies.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList: DataModel = gson.fromJson(br, DataModel::class.java)

        listFragment.binData(dataList = dataList)

        updateBanner(dataList)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if(hasFocus){
            var elapsed = System.currentTimeMillis() - startTime
            Log.d("TIEMPO", "MainActivity - Tiempo hasta que la pantalla está visible: ${elapsed} ms")
        }
    }

    fun updateBanner(dataList: DataModel) {
        binding.tvTitle.text = dataList.result[0].details[3].title
        binding.tvDescription.text = dataList.result[0].details[3].overview

        Glide.with(this)
            .load(getBannerImage(dataList.result[0].details[3].backdrop_path))
            .into(binding.imgBanner)
    }
}