package com.blogspot.ecommerce_pijush.login

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.blogspot.ecommerce_pijush.R
import com.blogspot.ecommerce_pijush.databinding.FragmentLoginBinding
import com.blogspot.ecommerce_pijush.model.Constants.CURRENT_USER
import com.blogspot.ecommerce_pijush.model.Constants.SHARED_USER_PREF
import com.google.gson.Gson


class LoginFragment : Fragment() {
    private val viewModel:LoginViewModel by activityViewModels()
    private lateinit var binding:FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.edtUsername.text.toString(),
                binding.edtPassword.text.toString()
            )
        }
        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        viewModel.user.observe(viewLifecycleOwner, {
            if (it != null) {
                findNavController().navigate(R.id.action_loginFragment_to_productHomeFragment)
                if (binding.chkRememberMe.isChecked){

                }
            }
        })
        return binding.root
    }

    companion object {

    }
}