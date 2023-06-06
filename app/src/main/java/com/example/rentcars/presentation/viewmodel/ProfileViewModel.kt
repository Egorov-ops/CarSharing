package com.example.rentcars.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentcars.data.entity.ProfileEntity
import com.example.rentcars.data.repository.ProfileRepository
import com.example.rentcars.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _profile = MutableLiveData<ProfileEntity?>()
    val profile: LiveData<ProfileEntity?> = _profile


    fun getProfile(
        profileId: Int
    ) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val result = profileRepository.getProfile(profileId)) {
                    is ResultWrapper.Success -> {
                        _profile.postValue(result.value)
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

    fun updateProfile(
        profileId: Int,
        name: String,
        phone: String,
        email: String,
        region: String
    ) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val result = profileRepository.updateProfile(
                    profileId, name, phone, email, region
                )){}
                _isLoading.postValue(false)
            }
        }
    }
}