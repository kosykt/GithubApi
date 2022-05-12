package com.example.githubapi.utils.imageloader

import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.githubapi.R

class CoilImageLoader : AppImageLoader {


    override fun loadInto(url: String, container: ImageView) {
        container.load(url) {
            target(
                onSuccess = { result ->
                    container.setImageDrawable(result)
                },
                onError = {
                    container.setImageResource(R.drawable.ic_launcher_background)
                }
            )
            transformations(
                CircleCropTransformation(),
            )
        }
    }
}