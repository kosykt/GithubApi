package com.example.data.database

import com.example.data.database.model.FavouriteReposEntity
import com.example.data.database.model.FavouriteUserEntity
import com.example.data.repository.DatabaseRepository
import com.example.data.database.model.HistoryCacheRepoEntity
import com.example.data.database.model.HistoryCacheUserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class DatabaseRepositoryImpl(
    private val database: AppDatabase
) : DatabaseRepository {

    override suspend fun insertUsers(models: List<HistoryCacheUserEntity>) {
        withContext(Dispatchers.IO) { database.historyCacheUsersDao.insert(models) }
    }

    override suspend fun getUsers(): List<HistoryCacheUserEntity> {
        return withContext(Dispatchers.IO) { database.historyCacheUsersDao.getAll() }
    }

    override suspend fun insertRepos(models: List<HistoryCacheRepoEntity>) {
        withContext(Dispatchers.IO) { database.historyCacheReposDao.insert(models) }
    }

    override suspend fun getRepos(ownerId: String): List<HistoryCacheRepoEntity> {
        return withContext(Dispatchers.IO) { database.historyCacheReposDao.getReposByOwnerId(ownerId) }
    }

    override suspend fun saveFavouriteUser(user: FavouriteUserEntity) {
        withContext(Dispatchers.IO) { database.favouriteUserDao.insert(user) }
    }

    override suspend fun deleteFavouriteUser(user: FavouriteUserEntity) {
        withContext(Dispatchers.IO) { database.favouriteUserDao.delete(user) }
    }

    override fun getAllFavouriteUsers(): Flow<List<FavouriteUserEntity>> {
        return database.favouriteUserDao.getAll().flowOn(Dispatchers.IO)
    }

    override suspend fun saveFavouriteRepo(reposEntity: FavouriteReposEntity) {
        withContext(Dispatchers.IO) { database.favouriteReposDao.insert(reposEntity) }
    }

    override suspend fun deleteFavouriteRepo(reposEntity: FavouriteReposEntity) {
        withContext(Dispatchers.IO) { database.favouriteReposDao.delete(reposEntity) }
    }

    override fun getAllFavouriteRepos(): Flow<List<FavouriteReposEntity>> {
        return database.favouriteReposDao.getAll().flowOn(Dispatchers.IO)
    }

}