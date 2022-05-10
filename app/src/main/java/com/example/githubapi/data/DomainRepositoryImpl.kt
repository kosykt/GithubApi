package com.example.githubapi.data

import com.example.githubapi.data.network.model.UserDTO
import com.example.githubapi.ui.usersfragment.DomainUserModel
import com.example.githubapi.utils.dtoToListDomainUserModel
import com.example.githubapi.utils.dtoToListUserEntity
import com.example.githubapi.utils.entityToListDomainUserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DomainRepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
) {

    fun getUsers(isNetworkAvailable: Boolean): Flow<List<DomainUserModel>> {
        return when (isNetworkAvailable) {
            true -> {
                networkRepository.getUsers()
                    .map {
                        cacheUsers(it)
                        it.dtoToListDomainUserModel()
                    }
            }
            false -> {
                databaseRepository.getUsers().map {
                    it.entityToListDomainUserModel()
                }
            }
        }
    }

    private suspend fun cacheUsers(models: List<UserDTO>) {
        databaseRepository.insertUsers(models.dtoToListUserEntity())
    }
}