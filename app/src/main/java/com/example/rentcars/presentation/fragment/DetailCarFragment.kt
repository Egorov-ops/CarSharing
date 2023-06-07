package com.example.rentcars.presentation.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rentcars.R
import com.example.rentcars.data.entity.CarEntity
import com.example.rentcars.data.entity.StateOfCar
import com.example.rentcars.databinding.FragmentDetailCarBinding
import com.example.rentcars.presentation.CarMapper
import com.example.rentcars.presentation.viewmodel.CarsViewModel
import com.example.rentcars.utils.Consts.CAR_ENTITY
import com.example.rentcars.utils.getParcelableExt
import com.example.rentcars.utils.setImage


class DetailCarFragment : Fragment(R.layout.fragment_detail_car) {

    private val binding: FragmentDetailCarBinding by viewBinding()
    private val viewModel: CarsViewModel by activityViewModels()
    private var carEntity: CarEntity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            carEntity = it.getParcelableExt(CAR_ENTITY, CarEntity::class.java)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        carEntity?.let {
            binding.markTv.text = it.markAndModel
            binding.stateTv.text = CarMapper.mapStateOfCar(it.state)
            binding.typeTv.text = CarMapper.mapTypeOfCar(it.typeOfCar)
            binding.regionTv.text = it.region
            binding.descriptionTv.text = it.description
            binding.imageIv.setImage(it.image)
            carEntity = it
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
            builder.setPositiveButton("Да,удалить") { _, _ ->
                carEntity?.let { viewModel.deleteCar(it) }
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
            builder.setNegativeButton("Нет, оставить") { dialog, _ ->
                dialog.cancel()
            }

            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun handleDialogResponse(idResponse: Int) {
        when (idResponse) {
            0 -> {
                carEntity?.let { carEntity -> viewModel.updateStateCar(carEntity.state, StateOfCar.IN_FLIGHT) }
            }
            1 -> {
                carEntity?.let { carEntity -> viewModel.updateStateCar(carEntity.state, StateOfCar.ON_REPAIR) }
            }
            2 -> {
                carEntity?.let { carEntity -> viewModel.updateStateCar(carEntity.state, StateOfCar.SOLD) }
            }
            else -> {
                carEntity?.let { carEntity -> viewModel.updateStateCar(carEntity.state, StateOfCar.REST) }
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