package com.mramallo.lumieretv.presentation

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.mramallo.lumieretv.R
import com.mramallo.lumieretv.databinding.ActivityMainBinding
import com.mramallo.lumieretv.util.getWidthInPercent


class MainActivity : FragmentActivity(), View.OnKeyListener {
    private lateinit var binding: ActivityMainBinding
    private var SIDE_MENU = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSearch.setOnKeyListener(this)
        binding.btnHome.setOnKeyListener(this)
        binding.btnMovies.setOnKeyListener(this)
        binding.btnTv.setOnKeyListener(this)
        binding.btnSports.setOnKeyListener(this)
        binding.btnSettings.setOnKeyListener(this)
        binding.btnLanguage.setOnKeyListener(this)
        binding.btnGenre.setOnKeyListener(this)
        
        changeFragment(HomeFragment())
    }

    fun changeFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    override fun onKey(view: View?, i: Int, keyEvent: KeyEvent?): Boolean {
        when(i) {
            KeyEvent.KEYCODE_DPAD_LEFT -> {
                if(!SIDE_MENU) {
                    openMenu()
                    SIDE_MENU = true
                }
            }
            else -> {}
        }
        return false
    }

    fun openMenu(){
        binding.blfNavBar.requestLayout()
        binding.blfNavBar.layoutParams.width = getWidthInPercent(this, 16)
    }

}