package com.example.rentcars.presentation.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.rentcars.R
import com.example.rentcars.data.entity.StateOfCar
import com.example.rentcars.data.entity.TypeOfCar
import com.example.rentcars.databinding.FragmentAddAutoBinding
import com.example.rentcars.presentation.viewmodel.CarsViewModel
import com.example.rentcars.utils.invisible
import com.example.rentcars.utils.visible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.platform.MaterialContainerTransform
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class AddAutoFragment : Fragment(R.layout.fragment_add_auto) {

    private val binding: FragmentAddAutoBinding by viewBinding()
    private val viewModel: CarsViewModel by activityViewModels()

    private var imageUri: String? = null
    private var typeOfCar: TypeOfCar = TypeOfCar.NO_TRUCK


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.navContainer
            duration = 400.toLong()
            scrimColor = Color.WHITE
            setAllContainerColors(requireContext().getColor(R.color.white))
        }
        arguments?.let {

        }
    }

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            binding.showAttachedImgCard.visible()
            Glide.with(this)
                .load(uri)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.image)
            imageUri = uri.toString()
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listenerForCheckboxes()
        binding.attachImageBtn.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.closeBtn.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        binding.detachImgBtn.setOnClickListener {
            binding.showAttachedImgCard.invisible()
            imageUri = null
        }

        binding.createBtn.setOnClickListener {
            if (binding.titleEt.text?.trim().isNullOrEmpty() ||
                binding.regionEt.text?.trim().isNullOrEmpty() ||
                binding.descriptionEt.text?.trim().isNullOrEmpty()
            ) {
                binding.titleEt.error = "Поле обязательно"
                binding.descriptionEt.error = "Поле обязательно"
                binding.regionEt.error = "Поле обязательно"
            } else {
                viewModel.addCar(
                    markAndModel = binding.titleEt.text.toString(),
                    typeOfCar = typeOfCar,
                    description = binding.descriptionEt.text.toString(),
                    region = binding.regionEt.text.toString(),
                    state = StateOfCar.REST,
                    image = imageUri
                )
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }
    }

    private fun listenerForCheckboxes() {
        binding.noTruckCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.truckCheckbox.isChecked = false
                typeOfCar = TypeOfCar.NO_TRUCK
            } else {
                binding.truckCheckbox.isChecked = true
            }
        }
        binding.truckCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.noTruckCheckbox.isChecked = false
                typeOfCar = TypeOfCar.TRUCK
            } else {
                binding.noTruckCheckbox.isChecked = true
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            AddAutoFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}