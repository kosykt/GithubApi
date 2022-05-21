package com.example.domain

import com.example.domain.models.DomainUserModel

class DeleteFavouriteUserUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(userModel: DomainUserModel) = repository.deleteFavouriteUser(userModel)
}