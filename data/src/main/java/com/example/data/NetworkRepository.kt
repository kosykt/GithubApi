package com.example.data

import com.example.data.network.model.RepoDTO
import com.example.data.network.model.UserDTO

interface NetworkRepository {

    suspend fun getUsers(): List<UserDTO>
    suspend fun getRepos(url: String): List<RepoDTO>
}