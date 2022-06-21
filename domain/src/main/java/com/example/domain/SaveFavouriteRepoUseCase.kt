package com.example.domain

import com.example.domain.models.DomainRepoModel

class SaveFavouriteRepoUseCase(
    private val repository: DataSourceRepository
) {
    suspend fun execute(repoModel: DomainRepoModel) = repository.saveFavouriteRepo(repoModel)
}