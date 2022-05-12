package com.example.githubapi.utils.imageloader

import android.widget.ImageView

interface AppImageLoader {

    fun loadInto(url: String, container: ImageView)
}