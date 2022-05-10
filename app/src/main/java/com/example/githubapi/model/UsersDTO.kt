package com.example.githubapi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UsersDTO(
    @PrimaryKey
    val id: String,
    val login: String,
    val repos_url: String,
)
