package com.example.githubapi.data

import com.example.githubapi.data.network.model.UserDTO
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {

    suspend fun getUsers(): List<UserDTO>
}