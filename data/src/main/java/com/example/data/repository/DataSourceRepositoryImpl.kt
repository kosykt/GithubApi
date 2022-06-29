package com.example.data.repository

import com.example.data.*
import com.example.data.network.model.RepoDTO
import com.example.data.network.model.UserDTO
import com.example.domain.DataSourceRepository
import com.example.domain.UseCaseResponse
import com.example.domain.models.DomainRepoModel
import com.example.domain.models.DomainUserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataSourceRepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
) : DataSourceRepository {

    override suspend fun getUsersFromNetwork(): UseCaseResponse {
        try {
            val response = networkRepository.getUsers()
            return when {
                response.isSuccessful && response.body() != null -> {
                    cacheUsers(response.body()!!)
                    UseCaseResponse.Success(response.body()!!.toListDomainUserModel())
                }
                response.isSuccessful && response.body() == null -> {
                    UseCaseResponse.Error("Response body is null")
                }
                else -> {
                    UseCaseResponse.Error(response.message())
                }
            }
        } catch (e: Exception) {
            return UseCaseResponse.Error(e.message.toString())
        }
    }

    override suspend fun getUsersFromDatabase(): UseCaseResponse {
        return try {
            UseCaseResponse.Success(databaseRepository.getUsers().toListDomainUserModel())
        } catch (e: Exception) {
            UseCaseResponse.Error(e.message.toString())
        }
    }

    override suspend fun getReposFromNetwork(login: String): UseCaseResponse {
        try {
            val response = networkRepository.getRepos(login)
            return when {
                response.isSuccessful && response.body() != null -> {
                    cacheRepos(response.body()!!)
                    UseCaseResponse.Success(response.body()!!.toListDomainRepoModel())
                }
                response.isSuccessful && response.body() == null -> {
                    UseCaseResponse.Error("Response body is null")
                }
                else -> {
                    UseCaseResponse.Error(response.message())
                }
            }
        } catch (e: Exception) {
            return UseCaseResponse.Error(e.message.toString())
        }
    }

    override suspend fun getReposFromDatabase(ownerId: String): UseCaseResponse {
        return try {
            UseCaseResponse.Success(databaseRepository.getRepos(ownerId).toListDomainRepoModel())
        } catch (e: Exception) {
            UseCaseResponse.Error(e.message.toString())
        }
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