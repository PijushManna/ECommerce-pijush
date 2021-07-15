package com.blogspot.ecommerce_pijush.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.blogspot.ecommerce_pijush.R

class LoginActivity : AppCompatActivity() {
    //Variables
    private val viewModel:LoginViewModel by lazy {
        ViewModelProvider(this,LoginViewModel.Factory(application)).get(LoginViewModel::class.java)
    }
    private lateinit var navController: NavController
    //END Variables

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return true
    }
}