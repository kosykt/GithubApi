package com.example.data.network

import com.example.data.repository.NetworkRepository
import com.example.data.network.model.RepoDTO
import com.example.data.network.model.UserDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkRepositoryImpl(
    private val retrofitService: RetrofitService
) : NetworkRepository {

    override suspend fun getUsers(): List<UserDTO> {
        return withContext(Dispatchers.IO) { retrofitService.getUsers() }
    }

    override suspend fun getRepos(login: String): List<RepoDTO> {
        return withContext(Dispatchers.IO) { retrofitService.getRepos(login) }
    }
}