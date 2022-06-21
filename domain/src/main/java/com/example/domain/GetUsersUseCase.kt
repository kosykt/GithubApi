package com.example.domain

import com.example.domain.models.DomainUserModel

class GetUsersUseCase(
    private val repository: DataSourceRepository
) {
    suspend fun execute(isNetworkAvailable: Boolean): List<DomainUserModel>{
        return when(isNetworkAvailable){
            true -> repository.getUsersFromNetwork()
            false -> repository.getUsersFromDatabase()
        }
    }
}