package com.example.rentcars.data.repository

import com.example.rentcars.data.entity.ProfileEntity
import com.example.rentcars.utils.ResultWrapper

interface ProfileRepository {

    //
    suspend fun getProfile(profileId: String): ResultWrapper<ProfileEntity>

    suspend fun updateProfile(
        profileId: String,
        name: String,
        phone: String,
        region: String
    )
}