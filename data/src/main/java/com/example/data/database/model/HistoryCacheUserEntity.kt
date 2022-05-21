package com.example.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryCacheUserEntity(
    @PrimaryKey
    val id: String,
    val login: String,
    val avatarUrl: String,
)