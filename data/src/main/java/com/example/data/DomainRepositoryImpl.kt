package com.example.data

import com.example.domain.DomainRepository
import com.example.domain.models.DomainUserModel
import com.example.data.network.model.RepoDTO
import com.example.data.network.model.UserDTO

class DomainRepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
) : DomainRepository {

    override suspend fun getUsers(isNetworkAvailable: Boolean): List<DomainUserModel> {
        return when (isNetworkAvailable) {
            true -> {
                networkRepository.getUsers().let {
                    cacheUsers(it)
                    it.toListDomainUserModel()
                }
            }
            false -> {
                databaseRepository.getUsers().toListDomainUserModel()
            }
        }
    }

    override suspend fun getRepos(
        isNetworkAvailable: Boolean,
        url: String,
        ownerId: String
    ): List<com.example.domain.models.DomainRepoModel> {
        return when (isNetworkAvailable) {
            true -> {
                networkRepository.getRepos(url).let {
                    cacheRepos(it)
                    it.toListDomainRepoModel()
                }
            }
            else -> {
                databaseRepository.getRepos(ownerId).toListDomainRepoModel()
            }
        }
    }

    private suspend fun cacheRepos(models: List<RepoDTO>) {
        databaseRepository.insertRepos(models.toListRepoEntity())
    }

    private suspend fun cacheUsers(models: List<UserDTO>) {
        databaseRepository.insertUsers(models.toListUserEntity())
    }
}