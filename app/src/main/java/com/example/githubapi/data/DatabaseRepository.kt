package com.example.githubapi.data

import com.example.githubapi.data.database.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    suspend fun insertUsers(models: List<UserEntity>)
    suspend fun getUsers(): List<UserEntity>
}