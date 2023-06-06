package com.example.rentcars.data.repository

import com.example.rentcars.data.entity.ProfileEntity
import com.example.rentcars.utils.ResultWrapper

interface ProfileRepository {

    //
    suspend fun getProfile(profileId: Int): ResultWrapper<ProfileEntity>

    suspend fun updateProfile(
        profileId: Int,
        name: String,
        phone: String,
        email: String,
        region: String
    )
}