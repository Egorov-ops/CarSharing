package com.example.rentcars.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rentcars.data.entity.CarEntity
import com.example.rentcars.data.entity.StateOfCar
import com.example.rentcars.data.entity.TypeOfCar
import com.example.rentcars.databinding.ItemCarBinding
import com.example.rentcars.presentation.CarMapper
import com.example.rentcars.utils.isNullOrEmptyMy
import com.example.rentcars.utils.setImage


class CarAdapter(
    val onCarClicked: (carEntity: CarEntity) -> Unit
) : ListAdapter<CarEntity, CarAdapter.CarViewHolder>(DiffCallback) {

    companion object{

        object DiffCallback : DiffUtil.ItemCallback<CarEntity>() {
            override fun areItemsTheSame(
                oldItem: CarEntity,
                newItem: CarEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CarEntity,
                newItem: CarEntity): Boolean {
                return oldItem == newItem
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = ItemCarBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val item = currentList[position]
        holder.binding.apply {
            markTv.text = item.markAndModel
            typeTv.text = CarMapper.mapTypeOfCar(item.typeOfCar)
            stateTv.text = CarMapper.mapStateOfCar(item.state)
            imageIv.setImage(item.image)
            root.setOnClickListener {
                onCarClicked.invoke(item)
            }
        }

    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class CarViewHolder(val binding: ItemCarBinding) : RecyclerView.ViewHolder(binding.root)
}

