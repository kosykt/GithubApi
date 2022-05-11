package com.example.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    val id: String,
    val login: String,
    val repos_url: String,
)