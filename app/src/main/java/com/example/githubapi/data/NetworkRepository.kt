package com.example.githubapi.data

import com.example.githubapi.data.network.model.RepoDTO
import com.example.githubapi.data.network.model.UserDTO

interface NetworkRepository {

    suspend fun getUsers(): List<UserDTO>
    suspend fun getRepos(url: String): List<RepoDTO>
}