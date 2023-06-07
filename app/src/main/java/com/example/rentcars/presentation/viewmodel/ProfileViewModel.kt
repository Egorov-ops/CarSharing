package com.example.rentcars.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentcars.data.UserDataCredentials
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
    private val profileRepository: ProfileRepository,
    private val userDataCredentials: UserDataCredentials
): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _profile = MutableLiveData<ProfileEntity?>()
    val profile: LiveData<ProfileEntity?> = _profile

    fun saveToken(token: String) = userDataCredentials.saveToken(token)
    fun getToken(): String? = userDataCredentials.getToken()


    fun getProfile(
        profileId: String
    ) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val result = profileRepository.getProfile(profileId.toString())) {
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
        profileId: String,
        name: String,
        phone: String,
        region: String
    ) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val result = profileRepository.updateProfile(
                    profileId.toString(), name, phone, region
                )){}
                _isLoading.postValue(false)
            }
        }
    }
}