package com.blogspot.ecommerce_pijush.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.blogspot.ecommerce_pijush.R
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.logging.Handler

class NoInternetBroadcast(private val badge:BadgeDrawable) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context?.let { isOnline(it) } == true) {
            badge.backgroundColor = Color.GREEN
        } else {
            badge.backgroundColor = Color.RED
        }
    }

    private fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}