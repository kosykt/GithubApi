package com.example.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteUserEntity(
    @PrimaryKey
    val id: String
)
