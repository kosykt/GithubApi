package com.example.domain

class GetAllFavouriteUsersIdUseCase(
    private val repository: DomainRepository
) {
    fun execute() = repository.getAllFavouriteUsersId()
}