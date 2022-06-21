package com.example.data.repository

import com.example.data.*
import com.example.domain.DataSourceRepository
import com.example.domain.models.DomainUserModel
import com.example.data.network.model.RepoDTO
import com.example.data.network.model.UserDTO
import com.example.domain.models.DomainRepoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataSourceRepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
) : DataSourceRepository {

    override suspend fun getUsersFromNetwork(): List<DomainUserModel> {
        return networkRepository.getUsers().let {
            cacheUsers(it)
            it.toListDomainUserModel()
        }
    }

    override suspend fun getUsersFromDatabase(): List<DomainUserModel> {
        return databaseRepository.getUsers().toListDomainUserModel()
    }

    override suspend fun getReposFromNetwork(login: String): List<DomainRepoModel> {
        return networkRepository.getRepos(login).let {
            cacheRepos(it)
            it.toListDomainRepoModel()
        }
    }

    override suspend fun getReposFromDatabase(ownerId: String): List<DomainRepoModel> {
        return databaseRepository.getRepos(ownerId).toListDomainRepoModel()
    }

    override suspend fun saveFavouriteUser(user: DomainUserModel) {
        databaseRepository.saveFavouriteUser(user.toFavouriteUserEntity())
    }

    override suspend fun deleteFavouriteUser(user: DomainUserModel) {
        databaseRepository.deleteFavouriteUser(user.toFavouriteUserEntity())
    }

    override fun getAllFavouriteUsersId(): Flow<List<String>> {
        return databaseRepository.getAllFavouriteUsers().map { it.toListString() }
    }

    override suspend fun saveFavouriteRepo(repoModel: DomainRepoModel) {
        databaseRepository.saveFavouriteRepo(repoModel.toFavouriteReposEntity())
    }

    override suspend fun deleteFavouriteRepo(repoModel: DomainRepoModel) {
        databaseRepository.deleteFavouriteRepo(repoModel.toFavouriteReposEntity())
    }

    override fun getAllFavouriteReposId(): Flow<List<String>> {
        return databaseRepository.getAllFavouriteRepos().map { it.toListString() }
    }

    private suspend fun cacheRepos(models: List<RepoDTO>) {
        databaseRepository.insertRepos(models.toListRepoEntity())
    }

    private suspend fun cacheUsers(models: List<UserDTO>) {
        databaseRepository.insertUsers(models.toListUserEntity())
    }
}