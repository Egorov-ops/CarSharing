package com.example.rentcars.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rentcars.R
import com.example.rentcars.databinding.FragmentCarsBinding
import com.example.rentcars.presentation.adapter.CarAdapter
import com.example.rentcars.presentation.viewmodel.CarsViewModel
import com.example.rentcars.utils.Consts.CAR_ID
import com.google.android.material.transition.platform.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CarsFragment : Fragment(R.layout.fragment_cars) {

    private val binding: FragmentCarsBinding by viewBinding()

    private val viewModel: CarsViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCars()
        val listAdapter = CarAdapter(::onCarClicked)
        binding.list.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        viewModel.cars.observe(viewLifecycleOwner) {
            (binding.list.adapter as CarAdapter).submitList(it)
            binding.refreshLayout.isRefreshing = false
        }


        binding.refreshLayout.setOnRefreshListener {
            viewModel.getCars()
        }

        binding.addAutoBtn.setOnClickListener {
            // Переход на экран добавления авто
            val extras = FragmentNavigatorExtras(it to "transitionToCreateAuto")

            reenterTransition = MaterialElevationScale(true).apply {
                duration = 350.toLong()
            }
            findNavController().navigate(
                R.id.action_carsFragment_to_addAutoFragment,
                null,
                null,
                extras
            )
        }
    }

    private fun onCarClicked(carId: Int) {
        val bundle = Bundle()
        bundle.apply {
            putInt(CAR_ID, carId)
        }
        findNavController().navigate(R.id.action_carsFragment_to_detailCarFragment, bundle)
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            CarsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}