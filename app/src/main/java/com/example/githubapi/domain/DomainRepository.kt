package com.example.githubapi.domain

import com.example.githubapi.domain.models.DomainUserModel
import kotlinx.coroutines.flow.Flow

interface DomainRepository {

    suspend fun getUsers(isNetworkAvailable: Boolean): List<DomainUserModel>
}