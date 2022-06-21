package com.example.domain

class GetAllFavouriteUsersIdUseCase(
    private val repository: DataSourceRepository
) {
    fun execute() = repository.getAllFavouriteUsersId()
}