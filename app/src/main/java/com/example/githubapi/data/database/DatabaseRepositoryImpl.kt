package com.example.githubapi.data.database

import com.example.githubapi.data.DatabaseRepository
import com.example.githubapi.data.database.model.UserEntity
import kotlinx.coroutines.flow.Flow

class DatabaseRepositoryImpl(
    private val database: AppDatabase
) : DatabaseRepository {

    override suspend fun insertUsers(models: List<UserEntity>) {
        database.usersDao.insert(models)
    }

    override fun getUsers(): Flow<List<UserEntity>> {
        return database.usersDao.getAll()
    }
}