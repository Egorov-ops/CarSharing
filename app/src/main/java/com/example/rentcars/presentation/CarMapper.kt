package com.example.rentcars.presentation

import com.example.rentcars.data.entity.StateOfCar
import com.example.rentcars.data.entity.TypeOfCar

class CarMapper {

    companion object{
        fun mapTypeOfCar(typeOfCar: TypeOfCar): String {
            return when(typeOfCar){
                TypeOfCar.TRUCK -> "Грузовой"
                TypeOfCar.NO_TRUCK -> "Легковой"
            }
        }

        fun mapStateOfCar(stateOfCar: StateOfCar): String {
            return when(stateOfCar){
                StateOfCar.IN_FLIGHT -> "В рейсе"
                StateOfCar.ON_REPAIR -> "На ремонте"
                StateOfCar.SOLD -> "Продано"
                StateOfCar.REST -> "Простаивает"
            }
        }
    }
}