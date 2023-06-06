package com.example.rentcars.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rentcars.R
import com.example.rentcars.databinding.FragmentEditProfileBinding
import com.example.rentcars.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private val binding: FragmentEditProfileBinding by viewBinding()

    private val viewModel: ProfileViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveBtn.setOnClickListener {
            if(binding.nameEt.text?.trim()?.isNotEmpty() == true &&
                binding.phoneEt.text?.trim()?.isNotEmpty() == true &&
                binding.emailEt.text?.trim()?.isNotEmpty() == true &&
                binding.regionEt.text?.trim()?.isNotEmpty() == true){

                viewModel.updateProfile(
                    profileId = 1,
                    name = binding.nameEt.text.toString(),
                    phone = binding.phoneEt.text.toString(),
                    email = binding.emailEt.text.toString(),
                    region = binding.regionEt.text.toString()
                )
                activity?.onBackPressedDispatcher?.onBackPressed()
            }else{
                binding.nameEt.error = "Обязательное поле"
                binding.phoneEt.error = "Обязательное поле"
                binding.emailEt.error = "Обязательное поле"
                binding.regionEt.error = "Обязательное поле"
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}