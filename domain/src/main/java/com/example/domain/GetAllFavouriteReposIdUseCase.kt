package com.example.domain

class GetAllFavouriteReposIdUseCase(
    private val repository: DataSourceRepository
) {
    fun execute() = repository.getAllFavouriteReposId()
}