package com.blogspot.ecommerce_pijush.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.blogspot.ecommerce_pijush.R


class LoginHomeFragment : Fragment() {
    private val viewModel:LoginViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_login_home, container, false)
        val btnSignUp: Button = v.findViewById(R.id.btn_sign_up)
        val btnSignIn:Button = v.findViewById(R.id.btn_sign_in)

        btnSignUp.setOnClickListener{
            findNavController().navigate(R.id.action_loginHomeFragment_to_registerFragment)
        }
        btnSignIn.setOnClickListener{
            findNavController().navigate(R.id.action_loginHomeFragment_to_loginFragment)
        }
        return v
    }
}