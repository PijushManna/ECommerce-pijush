package com.blogspot.ecommerce_pijush.login

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.ecommerce_pijush.model.User
import com.blogspot.ecommerce_pijush.model.UserVerificationStatus
import com.blogspot.ecommerce_pijush.model.UserVerificationStatus.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginViewModel : ViewModel() {
    val status = MutableLiveData<UserVerificationStatus>()

    private val _currentUser = MutableLiveData<User?>()
    val user:LiveData<User?> = _currentUser

    private val usersRef = FirebaseDatabase.getInstance().getReference("Users")
    init {

    }

    fun validateUserCredentials(mNumber: String){
        if (mNumber.isBlank() || !mNumber.isDigitsOnly()){
            status.value = INVALID
            return
        }

        usersRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i("Snapshot","$snapshot")
                if (snapshot.child(mNumber).exists()){
                    status.value = ALREADY_EXIST
                }else {
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
                _currentUser.value = user
            }
        }
    }

    fun login(username:String, password:String){
        usersRef.child(username).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("password").value == password){
                    _currentUser.value = snapshot.getValue(User::class.java)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                _currentUser.value = null
            }

        })
    }

}