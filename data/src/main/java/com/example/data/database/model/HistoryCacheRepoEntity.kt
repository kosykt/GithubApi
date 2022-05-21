package com.example.data.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = HistoryCacheUserEntity::class,
            parentColumns = ["id"],
            childColumns = ["ownerId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HistoryCacheRepoEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val ownerId: String,
)