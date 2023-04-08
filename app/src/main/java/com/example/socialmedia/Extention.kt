package com.example.socialmedia

import  android.widget.ImageView
import com.bumptech.glide.Glide


fun ImageView.loadImage(usl: String){
    val glide = Glide.with(context)
    glide.load(usl).centerCrop().placeholder(R.drawable.image2).into(this)

}
