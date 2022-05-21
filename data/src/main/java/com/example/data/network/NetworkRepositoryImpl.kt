package com.example.data.network

import com.example.data.repository.NetworkRepository
import com.example.data.network.model.RepoDTO
import com.example.data.network.model.UserDTO

class NetworkRepositoryImpl(
    private val retrofitService: RetrofitService
) : NetworkRepository {

    override suspend fun getUsers(): List<UserDTO> {
        return retrofitService.getUsers()
    }

    override suspend fun getRepos(login: String): List<RepoDTO> {
        return retrofitService.getRepos(login)
    }
}