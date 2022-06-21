package com.example.domain

import com.example.domain.models.DomainRepoModel

class DeleteFavouriteRepoUseCase(
    private val repository: DataSourceRepository
) {
    suspend fun execute(repoModel: DomainRepoModel) = repository.deleteFavouriteRepo(repoModel)
}