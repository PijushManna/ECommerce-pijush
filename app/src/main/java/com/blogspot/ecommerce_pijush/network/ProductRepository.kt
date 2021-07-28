package com.blogspot.ecommerce_pijush.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.blogspot.ecommerce_pijush.database.RoomProductDatabase
import com.blogspot.ecommerce_pijush.database.asDomainModel
import com.blogspot.ecommerce_pijush.models.Product
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductRepository(val database:RoomProductDatabase) {
    private var allData = listOf<Product>()


    var data = MutableLiveData<List<Product>>()

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
            val proRef = FirebaseDatabase.getInstance().reference.child("Product")
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

            fetchLocalData()
        }
    }
}