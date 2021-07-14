package com.blogspot.ecommerce_pijush

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.blogspot.ecommerce_pijush.login.LoginViewModel

class MainActivity : AppCompatActivity() {
    //Variables
    private val viewModel:LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }
    //END Variables

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}