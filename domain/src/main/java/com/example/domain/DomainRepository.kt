package com.example.domain

import com.example.domain.models.DomainRepoModel
import com.example.domain.models.DomainUserModel

interface DomainRepository {

    suspend fun getUsers(isNetworkAvailable: Boolean): List<DomainUserModel>
    suspend fun getRepos(
        isNetworkAvailable: Boolean,
        url: String,
        ownerId: String
    ): List<DomainRepoModel>
}