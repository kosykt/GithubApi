package com.example.githubapi.data

import com.example.githubapi.data.network.model.UserDTO
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {

    fun getUsers(): Flow<List<UserDTO>>
}