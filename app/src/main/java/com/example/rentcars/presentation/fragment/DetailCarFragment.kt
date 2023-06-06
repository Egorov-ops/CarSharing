package com.example.rentcars.presentation.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rentcars.R
import com.example.rentcars.data.entity.StateOfCar
import com.example.rentcars.databinding.FragmentDetailCarBinding
import com.example.rentcars.presentation.CarMapper
import com.example.rentcars.presentation.viewmodel.CarsViewModel
import com.example.rentcars.utils.Consts.CAR_ID
import com.example.rentcars.utils.setImage


class DetailCarFragment : Fragment(R.layout.fragment_detail_car) {

    private val binding: FragmentDetailCarBinding by viewBinding()
    private val viewModel: CarsViewModel by activityViewModels()
    private var carId: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            carId = it.getInt(CAR_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carId?.let { viewModel.getCar(it) }
        viewModel.detailOfCar.observe(viewLifecycleOwner){
            if(it != null){
                binding.markTv.text = it.markAndModel
                binding.stateTv.text = CarMapper.mapStateOfCar(it.state)
                binding.typeTv.text = CarMapper.mapTypeOfCar(it.typeOfCar)
                binding.regionTv.text = it.region
                binding.descriptionTv.text = it.description
                binding.imageIv.setImage(it.image)
            }
        }

        binding.editStateBtn.setOnClickListener {
            val options = arrayOf("В рейсе", "На ремонте", "Продано", "Простаивает")
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Выберите вариант")
            builder.setItems(options) { _, which ->
                val selectedOption = options[which]
                binding.stateTv.text = selectedOption
                handleDialogResponse(which)
            }

            val dialog = builder.create()
            dialog.show()
        }

        binding.deleteBtn.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Вы точно хотите удалить авто?")
            builder.setPositiveButton("Да,удалить"){_, _ ->
                carId?.let { viewModel.deleteCar(it) }
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
            builder.setNegativeButton("Нет, оставить"){ dialog, _ ->
                dialog.cancel()
            }

            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun handleDialogResponse(idResponse: Int){
        when (idResponse) {
            0 -> {
                carId?.let { carId -> viewModel.updateStateCar(carId, StateOfCar.IN_FLIGHT) }
            }
            1 -> {
                carId?.let { carId -> viewModel.updateStateCar(carId, StateOfCar.ON_REPAIR) }
            }
            2 -> {
                carId?.let { carId -> viewModel.updateStateCar(carId, StateOfCar.SOLD) }
            }
            else -> {
                carId?.let { carId -> viewModel.updateStateCar(carId, StateOfCar.REST) }
            }
        }
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            DetailCarFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}