package com.blogspot.ecommerce_pijush.utils

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.ecommerce_pijush.R
import com.blogspot.ecommerce_pijush.database.RoomProduct
import com.blogspot.ecommerce_pijush.database.asDomainModel
import com.blogspot.ecommerce_pijush.models.Product
import com.blogspot.ecommerce_pijush.ui.ProductsAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imgURL")
fun bindImage(v:ImageView, imgUrl: String?){
    imgUrl?.let {
        val imgURI = it.toUri().buildUpon().scheme("https").build()
        Glide.with(v.context)
            .load(imgURI)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(v)
    }
}

@BindingAdapter("listData")
fun bindRecyclerAdapter(v: RecyclerView, data: List<Product>?){
    (v.adapter as ProductsAdapter?)?.submitList(data)
}

@BindingAdapter("cartData")
fun bindCartAdapter(v: RecyclerView, data: List<Product>?){
    (v.adapter as ProductsAdapter?)?.submitList(data)
}

@SuppressLint("SetTextI18n")
@BindingAdapter("price")
fun bindPrice(v:TextView, data: Double){
    data.let {
        v.text = "$$data"
    }
}