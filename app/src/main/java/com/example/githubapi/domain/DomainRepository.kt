package com.example.githubapi.domain

import com.example.githubapi.domain.models.DomainUserModel
import kotlinx.coroutines.flow.Flow

interface DomainRepository {

    fun getUsers(isNetworkAvailable: Boolean): Flow<List<DomainUserModel>>
}