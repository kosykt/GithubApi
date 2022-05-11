package com.example.domain

import com.example.domain.models.DomainRepoModel

class GetReposUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(
        isNetworkAvailable: Boolean,
        url: String,
        ownerId: String
    ): List<DomainRepoModel> {
        return when (isNetworkAvailable) {
            true -> repository.getReposFromNetwork(url)
            false -> repository.getReposFromDatabase(ownerId)
        }
    }
}