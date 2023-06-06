package com.example.rentcars.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RentCarsApplication : Application() {

    override fun onCreate(){
        super.onCreate()

    }
}