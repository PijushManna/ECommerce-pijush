package com.blogspot.ecommerce_pijush

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.blogspot.ecommerce_pijush.database.RoomProduct
import com.blogspot.ecommerce_pijush.database.asDomainModel
import com.blogspot.ecommerce_pijush.database.getOfflineDatabase
import com.blogspot.ecommerce_pijush.models.Product
import com.blogspot.ecommerce_pijush.network.ProductRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var _user = MutableLiveData<FirebaseUser>()
    val user:LiveData<FirebaseUser> = _user
    private val database = getOfflineDatabase(application)
    val repository = ProductRepository(database)
    init {
        _user.value = Firebase.auth.currentUser
        viewModelScope.launch {
            repository.refreshData()
        }
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
}