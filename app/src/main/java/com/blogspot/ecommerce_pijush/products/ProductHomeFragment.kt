package com.blogspot.ecommerce_pijush.products

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.blogspot.ecommerce_pijush.R
import com.blogspot.ecommerce_pijush.login.LoginViewModel
import com.blogspot.ecommerce_pijush.model.Constants.SHARED_USER_PREF

class ProductHomeFragment : Fragment() {

    private val viewModel:LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_product_home, container, false)
        val b: Button = v.findViewById(R.id.btnLogout)
        b.setOnClickListener {
            context?.getSharedPreferences(SHARED_USER_PREF, MODE_PRIVATE)?.edit()?.clear()?.apply()
            viewModel.logout()
        }
        return v
    }
}