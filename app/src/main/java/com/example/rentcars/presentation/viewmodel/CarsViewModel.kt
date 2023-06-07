package com.example.rentcars.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentcars.data.UserDataCredentials
import com.example.rentcars.data.entity.CarEntity
import com.example.rentcars.data.entity.StateOfCar
import com.example.rentcars.data.entity.TypeOfCar
import com.example.rentcars.data.repository.CarsRepository
import com.example.rentcars.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CarsViewModel @Inject constructor(
    private val carsRepository: CarsRepository,
    private val userDataCredentials: UserDataCredentials
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _cars = MutableLiveData<List<CarEntity>?>()
    val cars: LiveData<List<CarEntity>?> = _cars

    private val _detailOfCar = MutableLiveData<CarEntity?>()
    val detailOfCar: LiveData<CarEntity?> = _detailOfCar

    fun saveToken(token: String) = userDataCredentials.saveToken(token)


    fun getCars() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val result = carsRepository.getCars()) {
                    is ResultWrapper.Success -> {
                        _cars.postValue(result.value)
                    }
                    else -> {
                        if (result is ResultWrapper.GenericError) {


                        } else if (result is ResultWrapper.NetworkError) {
                        }
                    }
                }
                _isLoading.postValue(false)
            }
        }
    }

//    fun getCar(
//        id: Int
//    ) {
//        _isLoading.postValue(true)
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                when (val result = carsRepository.getCar(id)) {
//                    is ResultWrapper.Success -> {
//                        _detailOfCar.postValue(result.value)
//                    }
//                    else -> {
//                        if (result is ResultWrapper.GenericError) {
//
//
//                        } else if (result is ResultWrapper.NetworkError) {
//                        }
//                    }
//                }
//                _isLoading.postValue(false)
//            }
//        }
//    }

    fun getToken(): String? = userDataCredentials.getToken()

    fun addCar(
        carEntity: CarEntity,
    ) {
        _isLoading.postValue(true)
        carsRepository.addCar(carEntity)
        _isLoading.postValue(false)
    }


    fun deleteCar(
        carEntity: CarEntity,
    ) {
        _isLoading.postValue(true)
        carsRepository.deleteCar(carEntity)
        _isLoading.postValue(false)
    }

    fun updateStateCar(
        oldStateOfCar: StateOfCar,
        newStateOfCar: StateOfCar
    ) {
        _isLoading.postValue(true)
        carsRepository.changeStateCar(oldStateOfCar, newStateOfCar)
        _isLoading.postValue(false)
    }

}