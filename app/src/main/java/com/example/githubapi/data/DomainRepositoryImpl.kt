package com.example.githubapi.data

import com.example.githubapi.data.network.model.RepoDTO
import com.example.githubapi.data.network.model.UserDTO
import com.example.githubapi.domain.DomainRepository
import com.example.githubapi.domain.models.DomainRepoModel
import com.example.githubapi.domain.models.DomainUserModel
import com.example.githubapi.utils.toListDomainRepoModel
import com.example.githubapi.utils.toListDomainUserModel
import com.example.githubapi.utils.toListRepoEntity
import com.example.githubapi.utils.toListUserEntity

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
    ): List<DomainRepoModel> {
        return  when (isNetworkAvailable){
            true -> {
                networkRepository.getRepos(url).let{
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