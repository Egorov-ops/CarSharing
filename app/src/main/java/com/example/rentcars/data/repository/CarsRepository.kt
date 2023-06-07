package com.example.rentcars.data.repository

import com.example.rentcars.data.entity.CarEntity
import com.example.rentcars.data.entity.StateOfCar
import com.example.rentcars.data.entity.TypeOfCar
import com.example.rentcars.utils.ResultWrapper

interface CarsRepository {

    suspend fun getCars(profileId: Int): ResultWrapper<List<CarEntity>>
    suspend fun getCar(id: Int): ResultWrapper<CarEntity>

    fun addCar(carEntity: CarEntity)

    fun deleteCar(carEntity: CarEntity)

    fun changeStateCar(
        oldStateOfCar: StateOfCar,
        newStateOfCar: StateOfCar
    )
}