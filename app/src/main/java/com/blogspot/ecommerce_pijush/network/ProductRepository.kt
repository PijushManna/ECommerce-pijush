package com.blogspot.ecommerce_pijush.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.blogspot.ecommerce_pijush.database.RoomProductDatabase
import com.blogspot.ecommerce_pijush.database.asDomainModel
import com.blogspot.ecommerce_pijush.models.Product
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductRepository(val database:RoomProductDatabase) {
    val allData:LiveData<List<Product>> = Transformations.map(database.roomProductDao.getAllData()){
        it.asDomainModel()
    }

    suspend fun refreshData(){
        withContext(Dispatchers.IO) {
            val proRef = FirebaseDatabase.getInstance().reference.child("Product")

            proRef.addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val prod = snapshot.getValue(Product::class.java)
                    prod?.let { it1 ->
                        CoroutineScope(Dispatchers.IO).launch {
                            database.roomProductDao.insertAll(it1.asDomainModel())
                        }
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val prod = snapshot.getValue(Product::class.java)
                    prod?.let { it1 ->
                        CoroutineScope(Dispatchers.IO).launch {
                            database.roomProductDao.insertAll(it1.asDomainModel())
                        }
                    }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    val prod = snapshot.getValue(Product::class.java)
                    prod?.let { it1 ->
                        CoroutineScope(Dispatchers.IO).launch {
                            database.roomProductDao.remove(it1.id)
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
        }
    }
}