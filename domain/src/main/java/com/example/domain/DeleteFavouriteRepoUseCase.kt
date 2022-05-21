package com.example.domain

import com.example.domain.models.DomainRepoModel

class DeleteFavouriteRepoUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(repoModel: DomainRepoModel) = repository.deleteFavouriteRepo(repoModel)
}