package com.example.rentcars.utils

fun String?.isNullOrEmptyMy(): Boolean {
    return this.isNullOrEmpty() || this == "null"
}