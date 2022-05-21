package com.example.domain

class GetAllFavouriteReposIdUseCase(
    private val repository: DomainRepository
) {
    fun execute() = repository.getAllFavouriteReposId()
}