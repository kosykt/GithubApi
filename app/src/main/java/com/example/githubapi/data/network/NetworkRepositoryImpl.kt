package com.example.githubapi.data.network

import com.example.githubapi.data.NetworkRepository
import com.example.githubapi.data.network.model.UserDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkRepositoryImpl(
    private val retrofitService: RetrofitService
) : NetworkRepository {

    override fun getUsers(): Flow<List<UserDTO>> {
        return flow { emit(retrofitService.getUsers()) }
    }
}