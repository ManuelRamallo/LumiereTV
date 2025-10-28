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
import com.mramallo.lumieretv.presentation.fragments.HomeFragment
import com.mramallo.lumieretv.presentation.fragments.SearchFragment
import com.mramallo.lumieretv.util.Constants
import com.mramallo.lumieretv.util.getWidthInPercent


class MainActivity : FragmentActivity(), View.OnKeyListener {
    private lateinit var binding: ActivityMainBinding
    private var isOpenSideMenu = false
    private var selectedMenu = Constants.MENU_HOME

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
        // Asegurarse de que solo se procesa el evento una vez (al presionar, no al soltar)
        if (keyEvent?.action == KeyEvent.ACTION_DOWN) {
            when(i) {
                KeyEvent.KEYCODE_DPAD_CENTER -> {
                    when(view?.id) {
                        R.id.btn_search -> {
                            selectedMenu = Constants.MENU_SEARCH
                            changeFragment(SearchFragment())
                        }
                        R.id.btn_home -> {
                            selectedMenu = Constants.MENU_HOME
                            changeFragment(HomeFragment())
                        }
                        R.id.btn_tv -> {
                            selectedMenu = Constants.MENU_TV
                            changeFragment(SearchFragment())
                        }
                        R.id.btn_movies -> {
                            selectedMenu = Constants.MENU_MOVIES
                            changeFragment(SearchFragment())
                        }
                        R.id.btn_sports -> {
                            selectedMenu = Constants.MENU_SPORTS
                            changeFragment(SearchFragment())
                        }
                        R.id.btn_language -> {
                            selectedMenu = Constants.MENU_LANGUAGE
                            changeFragment(SearchFragment())
                        }
                        R.id.btn_genre -> {
                            selectedMenu = Constants.MENU_GENRES
                            changeFragment(SearchFragment())
                        }
                        R.id.btn_settings -> {
                            selectedMenu = Constants.MENU_SETTINGS
                            changeFragment(SearchFragment())
                        }
                    }
                }
                KeyEvent.KEYCODE_DPAD_LEFT -> {
                    if(!isOpenSideMenu) {
                        openMenu()
                        isOpenSideMenu = true
                        return true // Indicar que el evento ha sido consumido
                    }
                }
                else -> {}
            }
        }
        return false // Devolver false para otros eventos o si no se consume
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if ( keyCode == KeyEvent.KEYCODE_DPAD_DOWN_RIGHT && isOpenSideMenu) {
            isOpenSideMenu = false
            closeMenu()
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        if(isOpenSideMenu) {
            isOpenSideMenu = false
            closeMenu()
        } else super.onBackPressed()
    }

    fun openMenu(){
        binding.blfNavBar.requestLayout()
        binding.blfNavBar.layoutParams.width = getWidthInPercent(this, 16)
    }

    fun closeMenu() {
        binding.blfNavBar.requestLayout()
        binding.blfNavBar.layoutParams.width = getWidthInPercent(this, 5)
    }

}