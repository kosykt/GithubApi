package com.example.data.repository

import com.example.data.database.model.FavouriteReposEntity
import com.example.data.database.model.FavouriteUserEntity
import com.example.data.database.model.HistoryCacheRepoEntity
import com.example.data.database.model.HistoryCacheUserEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    suspend fun insertUsers(models: List<HistoryCacheUserEntity>)
    suspend fun getUsers(): List<HistoryCacheUserEntity>
    suspend fun insertRepos(models: List<HistoryCacheRepoEntity>)
    suspend fun getRepos(ownerId: String): List<HistoryCacheRepoEntity>
    suspend fun saveFavouriteUser(user: FavouriteUserEntity)
    suspend fun deleteFavouriteUser(user: FavouriteUserEntity)
    fun getAllFavouriteUsers(): Flow<List<FavouriteUserEntity>>
    suspend fun saveFavouriteRepo(reposEntity: FavouriteReposEntity)
    suspend fun deleteFavouriteRepo(reposEntity: FavouriteReposEntity)
    fun getAllFavouriteRepos(): Flow<List<FavouriteReposEntity>>
}