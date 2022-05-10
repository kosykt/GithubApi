package com.example.githubapi.data

import com.example.githubapi.data.database.AppDatabase
import com.example.githubapi.data.network.RetrofitService
import com.example.githubapi.data.network.model.UserDTO
import com.example.githubapi.ui.usersfragment.DomainUserModel
import com.example.githubapi.utils.dtoToListDomainUserModel
import com.example.githubapi.utils.dtoToListUserEntity
import com.example.githubapi.utils.entityToListDomainUserModel

class DomainRepositoryImpl(
    private val retrofit: RetrofitService,
    private val database: AppDatabase,
) {

    suspend fun getUserFromNetwork(): List<DomainUserModel> {
        return retrofit.getUsers().let {
            cacheUsers(it)
            it.dtoToListDomainUserModel()
        }
    }

    suspend fun getUsersFromDataBase(): List<DomainUserModel> {
        return database.usersDao.getAll().entityToListDomainUserModel()
    }

    private suspend fun cacheUsers(models: List<UserDTO>) {
        database.usersDao.insert(models.dtoToListUserEntity())
    }
}