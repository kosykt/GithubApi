package com.example.domain

import com.example.domain.models.DomainRepoModel

class GetReposUseCase(
    private val repository: DataSourceRepository
) {
    suspend fun execute(
        isNetworkAvailable: Boolean,
        login: String,
        ownerId: String
    ): List<DomainRepoModel> {
        return when (isNetworkAvailable) {
            true -> repository.getReposFromNetwork(login)
            false -> repository.getReposFromDatabase(ownerId)
        }
    }
}