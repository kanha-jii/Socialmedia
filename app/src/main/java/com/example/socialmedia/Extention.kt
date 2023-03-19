package com.example.socialmedia

import  android.widget.ImageView
import com.bumptech.glide.Glide


fun ImageView.loadImage(usl: String){
    val glide = Glide.with(context)
    glide.load("https://source.unsplash.com/user/c_v_r/1600×900").into(this)

}