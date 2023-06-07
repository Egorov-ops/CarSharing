package com.example.rentcars.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.rentcars.data.UserDataCredentials
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userDataCredentials: UserDataCredentials

) : ViewModel() {
    fun isAuthorized(): Boolean {
        return userDataCredentials.getToken() != null
    }
}