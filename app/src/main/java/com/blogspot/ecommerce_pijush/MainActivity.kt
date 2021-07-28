package com.blogspot.ecommerce_pijush

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.blogspot.ecommerce_pijush.utils.NoInternetBroadcast
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private val viewModel:MainViewModel by lazy {
        ViewModelProvider(this, MainViewModel.Factory(application)).get(MainViewModel::class.java)
    }
    private lateinit var noInternet: NoInternetBroadcast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val bottomNavigationView:BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val badge = bottomNavigationView.getOrCreateBadge(R.id.mnu_account)
        noInternet = NoInternetBroadcast(badge)
        registerReceiver(noInternet, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(noInternet)
    }
}