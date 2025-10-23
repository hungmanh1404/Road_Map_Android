package com.example.road_map_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.road_map_android.data.vo.User
import com.example.road_map_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding
    private var currentFragment: Fragment? = null
    private var isFragmentVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initViews()
        setupClickListeners()
    }

    private fun setupBackPressedCallback() {

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val drawer = binding?.drawerLayout
                if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START)
                } else if (isFragmentVisible) {
                    showHome()
                } else {
                    finish()
                }
            }
        })
    }

    private fun initViews() {
        visibilityWebView(View.GONE)
        setupBackPressedCallback()
        setupDrawer()
        setSelectedMenuItem(R.id.nav_home) // Set Home as default selected
    }

    private fun setupClickListeners() {
        binding?.run {
            btnGoToActivity2.setOnClickListener {
                openActivity2()
            }

            btnOpenWebView.setOnClickListener {
                openWebView("https://www.google.com")
            }

            btnCloseWebView.setOnClickListener {
                visibilityWebView(View.GONE)
            }
        }
    }

    private fun setupDrawer() = binding?.run {
        setSupportActionBar(toolbar)
        ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ).apply {
            drawerLayout.addDrawerListener(this)
            syncState()
        }

//        drawerLayout.setOnTouchListener { _, event ->
//            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//                return@setOnTouchListener true
//            }
//            false
//        }


        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    showHome()
                    setSelectedMenuItem(R.id.nav_home)
                }

                R.id.nav_list -> {
                    showFragment(ListFragment())
                    setSelectedMenuItem(R.id.nav_list)
                }

                R.id.nav_profile -> {
                    showFragment(ProfileFragment())
                    setSelectedMenuItem(R.id.nav_profile)
                }

                R.id.nav_settings -> {
                    showFragment(SettingsFragment())
                    setSelectedMenuItem(R.id.nav_settings)
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun setSelectedMenuItem(menuItemId: Int) {
        binding?.navigationView?.menu?.let { menu ->
            // Reset all menu items to unchecked first
            resetAllMenuItems(menu)
            // Then set the selected item to checked
            menu.findItem(menuItemId)?.isChecked = true
        }
    }

    private fun resetAllMenuItems(menu: android.view.Menu) {
        menu.findItem(R.id.nav_home)?.isChecked = false
        menu.findItem(R.id.nav_list)?.isChecked = false
        menu.findItem(R.id.nav_profile)?.isChecked = false
        menu.findItem(R.id.nav_settings)?.isChecked = false
    }

    private fun showHome() = binding?.run {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        navigationView.setCheckedItem(R.id.nav_home)
        setSelectedMenuItem(R.id.nav_home)
        main.visibility = View.VISIBLE
        fragmentContainer.visibility = View.GONE
        currentFragment = null
        isFragmentVisible = false
    }

    private fun showFragment(fragment: Fragment) = binding?.run {
        main.visibility = View.GONE
        fragmentContainer.visibility = View.VISIBLE
        if (currentFragment != fragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
            currentFragment = fragment
        }
        isFragmentVisible = true
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }


    private fun visibilityWebView(state: Int) {
        binding?.run {
            btnCloseWebView.visibility = state
            webView.visibility = state
        }
    }

    private fun openWebView(url: String) {
        visibilityWebView(View.VISIBLE)
        binding?.webView?.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(url)
        }
    }

    private fun openActivity2() {
        Activity2.start(
            context = this,
            message = "Hello Activity 2",
            user = User("Manh Nguyen", 25)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        internal fun start(context: Context) {
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                context.startActivity(this)
            }
        }
    }
}
