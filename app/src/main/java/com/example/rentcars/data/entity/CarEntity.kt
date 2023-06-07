package com.example.rentcars.data.entity

import android.icu.text.CaseMap.Title
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarEntity(
    val id: String,
    val markAndModel: String,
    val typeOfCar: TypeOfCar,
    val description: String,
    val region: String,
    var state: StateOfCar,
    val image: String?
) : Parcelable

enum class StateOfCar(val title: String){
    IN_FLIGHT("1"), ON_REPAIR("2"), SOLD("3"), REST("4")
}

enum class TypeOfCar(val title: String){
    TRUCK("1"), NO_TRUCK("2")
}
