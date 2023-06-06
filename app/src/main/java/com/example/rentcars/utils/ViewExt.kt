package com.example.rentcars.utils

import android.view.View

fun View.visible() = this.apply {
    alpha = 0f
    visibility = View.VISIBLE

    animate()
        .alpha(1f)
        .setDuration(300)
        .setListener(null)
    // visibility = View.VISIBLE
}

fun View.invisible() = this.apply {
    alpha = 1f
    visibility = View.GONE

    animate()
        .alpha(0f)
        .setDuration(300)
        .setListener(null)
    //visibility = View.GONE
}