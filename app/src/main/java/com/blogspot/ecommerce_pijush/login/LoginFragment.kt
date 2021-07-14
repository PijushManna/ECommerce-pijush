package com.blogspot.ecommerce_pijush.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.blogspot.ecommerce_pijush.R
import com.blogspot.ecommerce_pijush.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private val viewModel:LoginViewModel by activityViewModels()
    private lateinit var binding:FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.btnLogin.setOnClickListener {
            viewModel.login(binding.edtUsername.text.toString(),binding.edtPassword.text.toString())
        }
        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        viewModel.user.observe(viewLifecycleOwner,{
            if (it != null){
                findNavController().navigate(R.id.action_loginFragment_to_productHomeFragment)
            }
        })
        return binding.root
    }
}