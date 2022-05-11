package com.example.githubapi.domain

class GetReposUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(isNetworkAvailable: Boolean, url: String, ownerId: String) =
        repository.getRepos(isNetworkAvailable, url, ownerId)
}