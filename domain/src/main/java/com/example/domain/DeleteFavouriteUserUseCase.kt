package com.example.domain

import com.example.domain.models.DomainUserModel

class DeleteFavouriteUserUseCase(
    private val repository: DataSourceRepository
) {
    suspend fun execute(userModel: DomainUserModel) = repository.deleteFavouriteUser(userModel)
}