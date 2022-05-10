package com.example.githubapi.domain

class GetUsersUseCase(
    private val repository: DomainRepository
) {
    fun execute(isNetworkAvailable: Boolean) = repository.getUsers(isNetworkAvailable)
}