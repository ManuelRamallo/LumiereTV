package com.mramallo.lumieretv.presentation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
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
    private var navBarAnimator: ValueAnimator? = null
    private val navMenuAnimationDuration = 280L
    lateinit var lastSelectedMenu: View

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


        lastSelectedMenu = binding.btnHome
        lastSelectedMenu.isActivated = true
        changeFragment(HomeFragment())
    }

    fun changeFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    override fun onKey(view: View?, i: Int, keyEvent: KeyEvent?): Boolean {
        if (keyEvent?.action == KeyEvent.ACTION_DOWN) {
            when(i) {
                KeyEvent.KEYCODE_DPAD_CENTER -> {

                    lastSelectedMenu.isActivated = false
                    view?.isActivated = true
                    lastSelectedMenu = view!!

                    when(view.id) {
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
                        switchToLastSelectedMenu()
                        openMenu()
                        isOpenSideMenu = true
                        return true
                    }
                }

                KeyEvent.KEYCODE_DPAD_RIGHT -> {
                    if (isOpenSideMenu) {
                        closeMenu()
                        return true
                    }
                }
                else -> {}
            }
        }
        return false // Devolver false para otros eventos o si no se consume
    }



    override fun onBackPressed() {
        if(isOpenSideMenu) {
            closeMenu()
        } else super.onBackPressed()
    }

    fun switchToLastSelectedMenu() {
        when(selectedMenu) {
            Constants.MENU_SEARCH -> {
                binding.btnSearch.requestFocus()
            }
            Constants.MENU_HOME -> {
                binding.btnHome.requestFocus()
            }
            Constants.MENU_TV -> {
                binding.btnTv.requestFocus()
            }
            Constants.MENU_MOVIES -> {
                binding.btnMovies.requestFocus()
            }
            Constants.MENU_SPORTS -> {
                binding.btnSports.requestFocus()
            }
            Constants.MENU_LANGUAGE -> {
                binding.btnLanguage.requestFocus()
            }
            Constants.MENU_GENRES -> {
                binding.btnGenre.requestFocus()
            }
            Constants.MENU_SETTINGS -> {
                binding.btnSettings.requestFocus()
            }
        }
    }

    fun openMenu() {
        animateNavBarWidth(getWidthInPercent(this, 16))
    }

    fun closeMenu() {
        if (!isOpenSideMenu) return
        isOpenSideMenu = false
        animateNavBarWidth(getWidthInPercent(this, 5)) {
            focusCurrentContent()
        }
    }

    fun openSideMenuFromContent() {
        if (!isOpenSideMenu) {
            switchToLastSelectedMenu()
            openMenu()
            isOpenSideMenu = true
        } else {
            switchToLastSelectedMenu()
        }
    }

    private fun focusCurrentContent() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
        if (currentFragment is ContentFocusable) {
            currentFragment.requestInitialFocus()
        } else {
            binding.container.requestFocus()
        }
    }

    private fun animateNavBarWidth(targetWidth: Int, onEnd: (() -> Unit)? = null) {
        val layoutParams = binding.blfNavBar.layoutParams
        val currentWidth = layoutParams.width
        if (currentWidth == targetWidth) {
            onEnd?.invoke()
            return
        }

        navBarAnimator?.cancel()

        navBarAnimator = ValueAnimator.ofInt(currentWidth, targetWidth).apply {
            setTextMenuOption(false)
            duration = navMenuAnimationDuration
            interpolator = DecelerateInterpolator()
            addUpdateListener { animator ->
                layoutParams.width = animator.animatedValue as Int
                binding.blfNavBar.layoutParams = layoutParams
                binding.blfNavBar.requestLayout()
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    navBarAnimator = null
                    setTextMenuOption(true)
                    onEnd?.invoke()
                }

                override fun onAnimationCancel(animation: Animator) {
                    navBarAnimator = null
                }
            })
        }

        navBarAnimator?.start()
    }

    private fun setTextMenuOption(haveText: Boolean) {
        binding.btnSearch.text = if (haveText) getString(R.string.menu_search) else null
        binding.btnHome.text = if (haveText) getString(R.string.menu_home) else null
        binding.btnMovies.text = if (haveText) getString(R.string.menu_movies) else null
        binding.btnTv.text = if (haveText) getString(R.string.menu_tv) else null
        binding.btnSports.text = if (haveText) getString(R.string.menu_sports) else null
        binding.btnSettings.text = if (haveText) getString(R.string.menu_settings) else null
        binding.btnLanguage.text = if (haveText) getString(R.string.menu_language) else null
        binding.btnGenre.text = if (haveText) getString(R.string.menu_genre) else null
    }

}

interface ContentFocusable {
    fun requestInitialFocus()
}