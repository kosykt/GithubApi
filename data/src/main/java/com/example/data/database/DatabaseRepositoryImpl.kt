package com.example.data.database

import com.example.data.repository.DatabaseRepository
import com.example.data.database.model.RepoEntity
import com.example.data.database.model.UserEntity

class DatabaseRepositoryImpl(
    private val database: AppDatabase
) : DatabaseRepository {

    override suspend fun insertUsers(models: List<UserEntity>) {
        database.usersDao.insert(models)
    }

    override suspend fun getUsers(): List<UserEntity> {
        return database.usersDao.getAll()
    }

    override suspend fun insertRepos(models: List<RepoEntity>) {
        database.reposDao.insert(models)
    }

    override suspend fun getRepos(ownerId: String): List<RepoEntity> {
        return database.reposDao.getReposByOwnerId(ownerId)
    }
}