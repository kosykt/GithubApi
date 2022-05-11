package com.example.githubapi.data.database

import com.example.githubapi.data.DatabaseRepository
import com.example.githubapi.data.database.model.RepoEntity
import com.example.githubapi.data.database.model.UserEntity

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