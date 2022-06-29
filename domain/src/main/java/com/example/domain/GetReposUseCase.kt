package com.example.domain

class GetReposUseCase(
    private val repository: DataSourceRepository
) {
    suspend fun execute(
        isNetworkAvailable: Boolean,
        login: String,
        ownerId: String
    ): UseCaseResponse {
        return when (isNetworkAvailable) {
            true -> repository.getReposFromNetwork(login)
            false -> repository.getReposFromDatabase(ownerId)
        }
    }
}