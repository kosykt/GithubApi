package com.example.data.repository

import com.example.domain.DomainRepository
import com.example.domain.models.DomainUserModel
import com.example.data.network.model.RepoDTO
import com.example.data.network.model.UserDTO
import com.example.data.toListDomainRepoModel
import com.example.data.toListDomainUserModel
import com.example.data.toListRepoEntity
import com.example.data.toListUserEntity
import com.example.domain.models.DomainRepoModel

class DomainRepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
) : DomainRepository {

    override suspend fun getUsersFromNetwork(): List<DomainUserModel> {
        return networkRepository.getUsers().let {
            cacheUsers(it)
            it.toListDomainUserModel()
        }
    }

    override suspend fun getUsersFromDatabase(): List<DomainUserModel> {
        return  databaseRepository.getUsers().toListDomainUserModel()
    }

    override suspend fun getReposFromNetwork(url: String): List<DomainRepoModel> {
        return networkRepository.getRepos(url).let {
            cacheRepos(it)
            it.toListDomainRepoModel()
        }
    }

    override suspend fun getReposFromDatabase(ownerId: String): List<DomainRepoModel> {
        return databaseRepository.getRepos(ownerId).toListDomainRepoModel()
    }

    private suspend fun cacheRepos(models: List<RepoDTO>) {
        databaseRepository.insertRepos(models.toListRepoEntity())
    }

    private suspend fun cacheUsers(models: List<UserDTO>) {
        databaseRepository.insertUsers(models.toListUserEntity())
    }
}