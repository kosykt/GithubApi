package com.example.githubapi.data.network

import com.example.githubapi.data.NetworkRepository
import com.example.githubapi.data.network.model.UserDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkRepositoryImpl(
    private val retrofitService: RetrofitService
) : NetworkRepository {

    override suspend fun getUsers(): List<UserDTO> {
        return retrofitService.getUsers()
    }
}