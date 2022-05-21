package com.example.data.database

import com.example.data.database.model.FavouriteReposEntity
import com.example.data.database.model.FavouriteUserEntity
import com.example.data.repository.DatabaseRepository
import com.example.data.database.model.HistoryCacheRepoEntity
import com.example.data.database.model.HistoryCacheUserEntity
import kotlinx.coroutines.flow.Flow

class DatabaseRepositoryImpl(
    private val database: AppDatabase
) : DatabaseRepository {

    override suspend fun insertUsers(models: List<HistoryCacheUserEntity>) {
        database.historyCacheUsersDao.insert(models)
    }

    override suspend fun getUsers(): List<HistoryCacheUserEntity> {
        return database.historyCacheUsersDao.getAll()
    }

    override suspend fun insertRepos(models: List<HistoryCacheRepoEntity>) {
        database.historyCacheReposDao.insert(models)
    }

    override suspend fun getRepos(ownerId: String): List<HistoryCacheRepoEntity> {
        return database.historyCacheReposDao.getReposByOwnerId(ownerId)
    }

    override suspend fun saveFavouriteUser(user: FavouriteUserEntity) {
        database.favouriteUserDao.insert(user)
    }

    override suspend fun deleteFavouriteUser(user: FavouriteUserEntity) {
        database.favouriteUserDao.delete(user)
    }

    override fun getAllFavouriteUsers(): Flow<List<FavouriteUserEntity>> {
        return database.favouriteUserDao.getAll()
    }

    override suspend fun saveFavouriteRepo(reposEntity: FavouriteReposEntity) {
        database.favouriteReposDao.insert(reposEntity)
    }

    override suspend fun deleteFavouriteRepo(reposEntity: FavouriteReposEntity) {
        database.favouriteReposDao.delete(reposEntity)
    }

    override fun getAllFavouriteRepos(): Flow<List<FavouriteReposEntity>> {
        return database.favouriteReposDao.getAll()
    }

}