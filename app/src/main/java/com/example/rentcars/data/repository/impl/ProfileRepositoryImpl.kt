package com.example.rentcars.data.repository.impl

import com.example.rentcars.data.entity.ProfileEntity
import com.example.rentcars.data.repository.ProfileRepository
import com.example.rentcars.utils.ResultWrapper
import com.example.rentcars.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor() : ProfileRepository {

    var profileData = ProfileEntity(
        id = "1",
        name = "Vlad Egorov",
        phone = "+79514462853",
        region = "Asia/Yekaterinburg"
    )

    override suspend fun getProfile(profileId: String): ResultWrapper<ProfileEntity> {
        return safeApiCall(Dispatchers.IO) {
            profileData
        }
    }

    override suspend fun updateProfile(
        profileId: String,
        name: String,
        phone: String,
        region: String
    ) {
        profileData = ProfileEntity(
            id = profileId,
            name,  phone, region
        )
    }
}