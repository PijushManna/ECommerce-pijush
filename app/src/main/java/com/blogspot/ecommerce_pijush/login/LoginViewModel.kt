package com.blogspot.ecommerce_pijush.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.*
import com.blogspot.ecommerce_pijush.model.Constants.CURRENT_USER
import com.blogspot.ecommerce_pijush.model.Constants.SHARED_USER_PREF
import com.blogspot.ecommerce_pijush.model.User
import com.blogspot.ecommerce_pijush.model.UserVerificationStatus
import com.blogspot.ecommerce_pijush.model.UserVerificationStatus.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

class LoginViewModel (application: Application) : AndroidViewModel(application){
    val status = MutableLiveData<UserVerificationStatus>()

    private val _currentUser = MutableLiveData<FirebaseUser>()
    val user:LiveData<FirebaseUser> = _currentUser

    private val auth = FirebaseAuth.getInstance()

    private val usersRef = FirebaseDatabase.getInstance().getReference("Users")

    init {

    }

    fun validateUserCredentials(mNumber: String){
        if (mNumber.isBlank() || !mNumber.isDigitsOnly()){
            status.value = INVALID
            return
        }

        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i("Snapshot", "$snapshot")
                if (snapshot.child(mNumber).exists()) {
                    status.value = ALREADY_EXIST
                } else {
                    status.value = NEW
                }
            }

            override fun onCancelled(error: DatabaseError) {
                status.value = INVALID
            }
        })
    }

    fun createNewUser(user: User){
        usersRef.child(user.number).setValue(user).addOnCompleteListener {
            if (it.isSuccessful){
                auth.createUserWithEmailAndPassword(user.email,user.password)
                _currentUser.value = auth.currentUser
            }
        }
    }

    fun login(username: String, password: String){
        usersRef.child(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("password").value == password) {

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun logout() {
        _currentUser.value = null
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}