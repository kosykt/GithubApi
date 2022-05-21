package com.example.data.network.model

import com.google.gson.annotations.SerializedName

data class UserDTO(
    val id: String,
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
)
