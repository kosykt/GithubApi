package com.example.domain

class GetUsersUseCase(
    private val repository: DataSourceRepository
) {
    suspend fun execute(isNetworkAvailable: Boolean): UseCaseResponse{
        return when(isNetworkAvailable){
            true -> repository.getUsersFromNetwork()
            false -> repository.getUsersFromDatabase()
        }
    }
}