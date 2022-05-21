package com.example.domain

import com.example.domain.models.DomainRepoModel

class SaveFavouriteRepoUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(repoModel: DomainRepoModel) = repository.saveFavouriteRepo(repoModel)
}