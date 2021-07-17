package com.blogspot.ecommerce_pijush.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.blogspot.ecommerce_pijush.MainViewModel
import com.blogspot.ecommerce_pijush.R
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
        return binding.root
    }
}