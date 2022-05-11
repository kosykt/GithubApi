package com.example.domain

class GetUsersUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(isNetworkAvailable: Boolean) = repository.getUsers(isNetworkAvailable)
}