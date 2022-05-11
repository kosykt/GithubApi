package com.example.githubapi.data

import com.example.githubapi.data.database.model.RepoEntity
import com.example.githubapi.data.database.model.UserEntity

interface DatabaseRepository {

    suspend fun insertUsers(models: List<UserEntity>)
    suspend fun getUsers(): List<UserEntity>
    suspend fun insertRepos(models: List<RepoEntity>)
    suspend fun getRepos(ownerId: String): List<RepoEntity>
}