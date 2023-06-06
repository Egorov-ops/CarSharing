package com.teamforce.thanksapp.di


import com.example.rentcars.data.repository.CarsRepository
import com.example.rentcars.data.repository.ProfileRepository
import com.example.rentcars.data.repository.impl.CarsRepositoryImpl
import com.example.rentcars.data.repository.impl.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BindModule {

    @Binds
    fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    fun bindCarsRepository(carsRepositoryImpl: CarsRepositoryImpl): CarsRepository

}