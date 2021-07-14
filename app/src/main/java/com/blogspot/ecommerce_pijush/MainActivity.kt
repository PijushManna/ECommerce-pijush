package com.blogspot.ecommerce_pijush

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blogspot.ecommerce_pijush.login.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

    }

    fun navigateToLogin(v: View){
        startActivity(Intent(this, LoginActivity::class.java))
    }


}