package com.blogspot.ecommerce_pijush

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.blogspot.ecommerce_pijush.database.getOfflineDatabase
import com.blogspot.ecommerce_pijush.models.Product
import com.blogspot.ecommerce_pijush.network.ProductRepository
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.firebase.ui.auth.util.ExtraConstants
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

class MainActivity : AppCompatActivity() {
    private val viewModel:MainViewModel by lazy {
        ViewModelProvider(this,MainViewModel.Factory(application)).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        viewModel.user

        val ref = FirebaseDatabase.getInstance().reference
        val key = ref.child("Product").push().key
        val item = Product(
                key!!,
                "shirt",
            "https://picsum.photos/200/300",
                12000.00,
                5.0,
                4.5f,
                9,
                true,
                120.00
        )
        ref.child("Product").child(key).setValue(item)
    }

}