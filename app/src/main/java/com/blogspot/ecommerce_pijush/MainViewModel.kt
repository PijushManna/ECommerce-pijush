package com.blogspot.ecommerce_pijush

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.*
import com.blogspot.ecommerce_pijush.database.getOfflineDatabase
import com.blogspot.ecommerce_pijush.models.Cart
import com.blogspot.ecommerce_pijush.network.ProductRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val ref = FirebaseDatabase.getInstance().reference
    private var _user = MutableLiveData<FirebaseUser>()
    private val user: LiveData<FirebaseUser> = _user
    private val database = getOfflineDatabase(application)
    val repository = ProductRepository(database)

    private val _cartIsVisible = MutableLiveData(View.GONE)
    val cartIsVisible: LiveData<Int> = _cartIsVisible

    private val cartItem  = HashMap<String,Int>()

    init {
        _user.value = Firebase.auth.currentUser
        repository.refreshData()
        clearCart()
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    private fun showCart(){
        _cartIsVisible.value = View.VISIBLE
    }

    private fun hideCart(){
        _cartIsVisible.value = View.INVISIBLE
    }

    fun addToCart(id: String, count: Int) {
        cartItem[id] = count
        if (count == 0){
            cartItem.remove(id)
        }

        if (cartItem.isEmpty()){
            hideCart()
        } else {
            showCart()
        }
        Log.d("Cart",cartItem.toString())
    }

    fun clearCart(){
        cartItem.clear()
    }

    fun submitCart() {
        if (user.value == null){
            Toast.makeText(getApplication(),"Please Login",Toast.LENGTH_SHORT).show()
        } else {
            ref.child("Cart").child(user.value!!.uid).child(System.currentTimeMillis().toString()).setValue(cartItem)
        }
    }
}