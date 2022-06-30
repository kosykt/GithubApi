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
                onStart = {
                    container.setImageResource(R.drawable.ar_loading_animation)
                },
                onError = {
                    container.setImageResource(R.drawable.ic_baseline_error_outline_24)
                }
            )
            transformations(
                CircleCropTransformation(),
            )
        }
    }
}