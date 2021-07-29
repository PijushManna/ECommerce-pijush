package com.blogspot.ecommerce_pijush.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.ecommerce_pijush.MainViewModel
import com.blogspot.ecommerce_pijush.R
import com.blogspot.ecommerce_pijush.databinding.LayoutPinkItemBinding
import com.blogspot.ecommerce_pijush.models.Product

class CartAdapter(private val viewModel: MainViewModel):
    ListAdapter<Product, CartAdapter.ViewHolder>(DiffCallback){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutPinkItemBinding.inflate(LayoutInflater.from(parent.context)),viewModel)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }
    class ViewHolder(private val binding: LayoutPinkItemBinding, private val viewModel: MainViewModel):
        RecyclerView.ViewHolder(binding.root){
        private var itemCount = 0
        private var bookmark = false
        fun bind(item: Product){
            binding.model = item
            binding.itemAdd.setOnClickListener {
                itemCount += 1
                binding.itemCount.text = itemCount.toString()
                viewModel.addToCart(item.id,itemCount)
            }
            binding.itemSub.setOnClickListener {
                if (itemCount>0){
                    itemCount -= 1
                    binding.itemCount.text = itemCount.toString()
                    viewModel.addToCart(item.id,itemCount)
                }
            }
            binding.itemBookmark.setOnClickListener {
                bookmark = if(!bookmark) {
                    binding.itemBookmark.setImageResource(R.drawable.ic_baseline_favorite_24)
                    true
                } else {
                    binding.itemBookmark.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    false
                }
            }

            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
    }

}