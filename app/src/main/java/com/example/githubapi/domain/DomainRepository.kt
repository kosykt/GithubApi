package com.example.githubapi.domain

import com.example.githubapi.domain.models.DomainRepoModel
import com.example.githubapi.domain.models.DomainUserModel

interface DomainRepository {

    suspend fun getUsers(isNetworkAvailable: Boolean): List<DomainUserModel>
    suspend fun getRepos(
        isNetworkAvailable: Boolean,
        url: String,
        ownerId: String
    ): List<DomainRepoModel>
}