package com.example.rentcars.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.rentcars.R

fun ImageView.setImage(photo: String?){
    Glide.with(this)
        .load(photo)
        .centerCrop()
        .error(R.drawable.placeholder)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}
