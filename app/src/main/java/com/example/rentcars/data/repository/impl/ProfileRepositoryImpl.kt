package com.example.rentcars.data.repository.impl

import com.example.rentcars.data.entity.ProfileEntity
import com.example.rentcars.data.repository.ProfileRepository
import com.example.rentcars.utils.ResultWrapper
import com.example.rentcars.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor() : ProfileRepository {

    var profileData = ProfileEntity(
        id = 1,
        name = "Dmitry Malkov",
        email = "mde.developer@gmail.com",
        phone = "+79514462853",
        region = "Asia/Yekaterinburg"
    )

    override suspend fun getProfile(profileId: Int): ResultWrapper<ProfileEntity> {
        return safeApiCall(Dispatchers.IO) {
            profileData
        }
    }

    override suspend fun updateProfile(
        profileId: Int,
        name: String,
        phone: String,
        email: String,
        region: String
    ) {
        profileData = ProfileEntity(
            id = profileId,
            name, email, phone, region
        )
    }
}