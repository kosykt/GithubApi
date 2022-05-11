package com.example.githubapi.data.network

import com.example.githubapi.data.NetworkRepository
import com.example.githubapi.data.network.model.RepoDTO
import com.example.githubapi.data.network.model.UserDTO

class NetworkRepositoryImpl(
    private val retrofitService: RetrofitService
) : NetworkRepository {

    override suspend fun getUsers(): List<UserDTO> {
        return retrofitService.getUsers()
    }

    override suspend fun getRepos(url: String): List<RepoDTO> {
        return retrofitService.getRepos(url)
    }
}