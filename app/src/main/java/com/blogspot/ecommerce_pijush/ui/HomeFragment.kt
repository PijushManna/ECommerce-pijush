package com.blogspot.ecommerce_pijush.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.blogspot.ecommerce_pijush.MainViewModel
import com.blogspot.ecommerce_pijush.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private val productViewModel: MainViewModel by activityViewModels()
    private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.model = productViewModel
        binding.lifecycleOwner = this
        binding.rcrItems.adapter = ProductsAdapter(productViewModel)
        binding.scrItems.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { productViewModel.repository.useFilter(it) }
                return true
            }
        })
        binding.srlItems.setOnRefreshListener {
            productViewModel.repository.refreshData()
            binding.srlItems.isRefreshing = false
            productViewModel.clearCart()
        }

        binding.btnAddToCart.setOnClickListener{
            productViewModel.submitCart()
        }
        return binding.root
    }
}