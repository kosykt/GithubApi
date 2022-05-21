package com.example.domain

import com.example.domain.models.DomainRepoModel
import com.example.domain.models.DomainUserModel
import kotlinx.coroutines.flow.Flow

interface DomainRepository {

    suspend fun getUsersFromNetwork(): List<DomainUserModel>
    suspend fun getUsersFromDatabase(): List<DomainUserModel>
    suspend fun getReposFromNetwork(url: String): List<DomainRepoModel>
    suspend fun getReposFromDatabase(ownerId: String): List<DomainRepoModel>
    suspend fun saveFavouriteUser(user: DomainUserModel)
    suspend fun deleteFavouriteUser(user: DomainUserModel)
    fun getAllFavouriteUsersId(): Flow<List<String>>
}