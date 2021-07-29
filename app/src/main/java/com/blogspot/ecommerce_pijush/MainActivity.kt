package com.blogspot.ecommerce_pijush

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.blogspot.ecommerce_pijush.utils.NoInternetBroadcast
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private val viewModel:MainViewModel by lazy {
        ViewModelProvider(this, MainViewModel.Factory(application)).get(MainViewModel::class.java)
    }
    private lateinit var noInternet: NoInternetBroadcast
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val bottomNavigationView:BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController
        val badge = bottomNavigationView.getOrCreateBadge(R.id.mnu_account)
        noInternet = NoInternetBroadcast(badge)
        registerReceiver(noInternet, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mnu_orders -> {
                    navController.navigate(R.id.cartFragment)
                }
                R.id.mnu_home -> {
                    navController.navigate(R.id.homeFragment)
                }
                else -> {
                    navController.navigate(R.id.homeFragment)
                }
            }

            true
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(noInternet)
    }
}