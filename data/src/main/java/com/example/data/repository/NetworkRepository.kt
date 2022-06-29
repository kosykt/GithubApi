package com.example.data.repository

import com.example.data.network.model.RepoDTO
import com.example.data.network.model.UserDTO
import retrofit2.Response

interface NetworkRepository {

    suspend fun getUsers(): Response<List<UserDTO>>
    suspend fun getRepos(login: String): Response<List<RepoDTO>>
}