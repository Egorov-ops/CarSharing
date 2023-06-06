package com.example.rentcars.presentation.fragment

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rentcars.R
import com.example.rentcars.databinding.FragmentProfileBinding
import com.example.rentcars.presentation.viewmodel.ProfileViewModel
import com.example.rentcars.utils.invisible
import com.example.rentcars.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding: FragmentProfileBinding by viewBinding()

    private val viewModel: ProfileViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadProfileData()
        setProfileData()
        expandingInfoCard()

        binding.editBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
    }

    private fun setProfileData() {
        viewModel.profile.observe(viewLifecycleOwner){ profile ->
            if(profile != null){
                binding.nameTv.text = profile.name
                binding.emailTv.text = profile.email
                binding.phoneTv.text = profile.phone
                binding.gpsTv.text = profile.region
            }
        }
    }

    private fun loadProfileData() {
        viewModel.getProfile(1)
    }

    private fun expandingInfoCard(){
        var isExpanded = false
        binding.expandBtn.setOnClickListener {
            animExpandindCard(isExpanded)
            isExpanded = !isExpanded
        }
    }

    private fun animExpandindCard(isExpanded: Boolean){
        val expandDuration = 300L // Длительность анимации в миллисекундах
        if (isExpanded) {
            // Если карточка уже раскрыта, скрываем ее
            binding.contentCardLinear.animate().apply {
                translationYBy(-binding.contentCardLinear.height.toFloat())
                alpha(0f)
                duration = expandDuration
                start()
                binding.contentCardLinear.invisible()
            }
            val anim = ValueAnimator.ofInt(600, 160)
            anim.addUpdateListener { valueAnimator ->
                val height = valueAnimator.animatedValue as Int
                val layoutParams = binding.infoCard.layoutParams
                layoutParams.height = height
                binding.infoCard.layoutParams = layoutParams
            }
            anim.duration = expandDuration
            anim.start()
            binding.expandBtn.animate().apply {
                rotation(90f)
                duration = expandDuration
                start()
            }
        } else {
            // Если карточка скрыта, показываем ее
            binding.contentCardLinear.animate().apply {
                translationYBy(binding.contentCardLinear.height.toFloat())
                alpha(1f)
                duration = expandDuration
                start()
                binding.contentCardLinear.visible()
            }
            val anim = ValueAnimator.ofInt(160, 600)
            anim.addUpdateListener { valueAnimator ->
                val height = valueAnimator.animatedValue as Int
                val layoutParams = binding.infoCard.layoutParams
                layoutParams.height = height
                binding.infoCard.layoutParams = layoutParams
            }
            anim.duration = expandDuration
            anim.start()
            binding.expandBtn.animate().apply {
                rotation(-90f)
                duration = expandDuration
                start()
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}