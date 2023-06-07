package com.example.rentcars.utils

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

fun <T: Parcelable> Bundle.getParcelableExt(key: String, objectClass: Class<T>): T? {
    return if(Build.VERSION.SDK_INT >= 33){
        this.getParcelable(key, objectClass)
    }else{
        this.getParcelable(key)
    }
}