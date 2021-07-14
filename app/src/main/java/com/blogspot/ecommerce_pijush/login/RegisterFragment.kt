package com.blogspot.ecommerce_pijush.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.blogspot.ecommerce_pijush.R
import com.blogspot.ecommerce_pijush.databinding.FragmentRegisterBinding
import com.blogspot.ecommerce_pijush.model.User
import com.blogspot.ecommerce_pijush.model.UserVerificationStatus.*
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        binding.registerModel = viewModel
        binding.lifecycleOwner = this

        binding.btnRegisterSubmit.setOnClickListener {
            viewModel.validateUserCredentials(binding.tetMobileNumber.text.toString().trim())
        }
        binding.btnRegisterCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.status.observe(viewLifecycleOwner, {
            when (it) {
                ALREADY_EXIST -> {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.number_already_exist),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                NEW -> {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.creating_new_account),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    val newUser = User(
                        binding.tetFullName.text.toString(),
                        binding.tetMobileNumber.text.toString(),
                        binding.tetAreaCode.text.toString(),
                        binding.tetAddress.text.toString(),
                        binding.tetEmailAddress.text.toString(),
                        binding.tetDateOfBirth.text.toString(),
                        binding.tetPassword.text.toString()
                    )
                    viewModel.createNewUser(newUser)
                    binding.btnRegisterSubmit.isEnabled = false
                    binding.btnRegisterCancel.isEnabled = false
                }
                INVALID -> {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.invalid_number),
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
                else -> {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.database_error),
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
            }
        })
        viewModel.user.observe(viewLifecycleOwner,{
            if (it != null){
                findNavController().navigate(R.id.action_registerFragment_to_productHomeFragment)
            }
        })
        return binding.root
    }
}