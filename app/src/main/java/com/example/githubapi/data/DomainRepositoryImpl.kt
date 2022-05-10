package com.example.githubapi.data

import com.example.githubapi.data.network.model.UserDTO
import com.example.githubapi.domain.DomainRepository
import com.example.githubapi.domain.models.DomainUserModel
import com.example.githubapi.utils.dtoToListDomainUserModel
import com.example.githubapi.utils.dtoToListUserEntity
import com.example.githubapi.utils.entityToListDomainUserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DomainRepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
) : DomainRepository {

    override suspend fun getUsers(isNetworkAvailable: Boolean): List<DomainUserModel> {
        return when (isNetworkAvailable) {
            true -> {
                networkRepository.getUsers().let {
                    cacheUsers(it)
                    it.dtoToListDomainUserModel()
                }
            }
            false -> {
                databaseRepository.getUsers().entityToListDomainUserModel()
            }
        }
    }

    private suspend fun cacheUsers(models: List<UserDTO>) {
        databaseRepository.insertUsers(models.dtoToListUserEntity())
    }
}