package com.example.domain

import com.example.domain.models.DomainRepoModel
import com.example.domain.models.DomainUserModel
import kotlinx.coroutines.flow.Flow

interface DataSourceRepository {

    suspend fun getUsersFromNetwork(): UseCaseResponse
    suspend fun getUsersFromDatabase(): UseCaseResponse
    suspend fun getReposFromNetwork(login: String): UseCaseResponse
    suspend fun getReposFromDatabase(ownerId: String): UseCaseResponse
    suspend fun saveFavouriteUser(user: DomainUserModel)
    suspend fun deleteFavouriteUser(user: DomainUserModel)
    fun getAllFavouriteUsersId(): Flow<List<String>>
    suspend fun saveFavouriteRepo(repoModel: DomainRepoModel)
    suspend fun deleteFavouriteRepo(repoModel: DomainRepoModel)
    fun getAllFavouriteReposId(): Flow<List<String>>
}