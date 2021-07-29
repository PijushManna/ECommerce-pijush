package com.blogspot.ecommerce_pijush.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.blogspot.ecommerce_pijush.database.RoomProductDatabase
import com.blogspot.ecommerce_pijush.database.asDomainModel
import com.blogspot.ecommerce_pijush.models.Cart
import com.blogspot.ecommerce_pijush.models.Product
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductRepository(val database:RoomProductDatabase) {
    //Variables
    private var allData = listOf<Product>()
    var data = MutableLiveData<List<Product>>()

    var cartItems = MutableLiveData<ArrayList<Cart>>()
    //END Variables

    //Methods
    private fun fetchLocalData() {
        CoroutineScope(Dispatchers.Main).launch {
            allData = database.roomProductDao.getAllData().asDomainModel()
            data.value = allData
        }
    }

    fun useFilter(query: String) {
        data.value = allData.filter {
            it.type.contains(query, true)
        }
    }

    fun refreshData(){
        CoroutineScope(Dispatchers.IO).launch {
            val instance = FirebaseDatabase.getInstance().reference
            val proRef = instance.child("Product")
            val cartRef = instance.child("Cart")
            proRef.orderByChild("name")
            proRef.addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val prod = snapshot.getValue(Product::class.java)
                    prod?.let { it1 ->
                        CoroutineScope(Dispatchers.IO).launch {
                            database.roomProductDao.insertAll(it1.asDomainModel())
                            fetchLocalData()
                        }
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val prod = snapshot.getValue(Product::class.java)
                    prod?.let { it1 ->
                        CoroutineScope(Dispatchers.IO).launch {
                            database.roomProductDao.insertAll(it1.asDomainModel())
                            fetchLocalData()
                        }
                    }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    val prod = snapshot.getValue(Product::class.java)
                    prod?.let { it1 ->
                        CoroutineScope(Dispatchers.IO).launch {
                            database.roomProductDao.remove(it1.id)
                            fetchLocalData()
                        }
                    }
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    val prod = snapshot.getValue(Product::class.java)
                    prod?.let { it1 ->
                        CoroutineScope(Dispatchers.IO).launch {
                            database.roomProductDao.insertAll(it1.asDomainModel())

                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase Error", error.message)
                }
            })

            if (Firebase.auth.uid != null){
                cartRef.child(Firebase.auth.uid!!).addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.iterator().forEach{
                            val items = it.value!!
                            val cart = Cart(it.key!!, items as HashMap<String, Int?>)
                            cartItems.value?.add(cart)
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("Cart","cancelled")
                    }

                })
            }

            fetchLocalData()
        }
    }
}

