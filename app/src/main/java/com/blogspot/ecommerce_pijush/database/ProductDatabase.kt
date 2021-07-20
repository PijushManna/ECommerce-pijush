package com.blogspot.ecommerce_pijush.database

import android.content.Context
import androidx.room.*
import com.blogspot.ecommerce_pijush.models.Product


@Entity
data class RoomProduct constructor(
    @PrimaryKey
    val id: String,
    val name: String,
    val type: String,
    val imgSrc: String,
    val price: Double,
    val discount: Double,
    val rating: Float,
    val ratingCount: Int,
    val trusted: Boolean,
    val shippingCost: Double
)

fun List<RoomProduct>.asDomainModel():List<Product>{
    return map {
        Product(
            it.id,
            it.name,
            it.type,
            it.imgSrc,
            it.price,
            it.discount,
            it.rating,
            it.ratingCount,
            it.trusted,
            it.shippingCost
        )
    }
}

@Dao
interface RoomProductDao {
    @Query("select * from RoomProduct")
    fun getAllData(): List<RoomProduct>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg item: RoomProduct)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: RoomProduct)

    @Query("Delete from RoomProduct where RoomProduct.id = :key")
    fun remove(key: String)
}

@Database(entities = [RoomProduct::class],version = 1,exportSchema = false)
abstract class RoomProductDatabase : RoomDatabase() {
    abstract val roomProductDao: RoomProductDao
}

private lateinit var INSTANCE: RoomProductDatabase


fun getOfflineDatabase(context: Context):RoomProductDatabase{
    synchronized(RoomProductDatabase::class.java){
        if (!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext, RoomProductDatabase::class.java, "RoomProductDatabase")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                .build()
        }
        return INSTANCE
    }
}