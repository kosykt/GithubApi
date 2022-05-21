package com.example.domain

import com.example.domain.models.DomainUserModel

class SaveFavouriteUserUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(userModel: DomainUserModel) = repository.saveFavouriteUser(userModel)
}