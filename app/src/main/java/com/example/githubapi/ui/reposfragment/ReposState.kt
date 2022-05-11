package com.example.githubapi.ui.reposfragment

import com.example.domain.models.DomainRepoModel

sealed class ReposState {
    data class Success(val response: List<DomainRepoModel>) : ReposState()
    data class Error(val error: String) : ReposState()
    object Loading : ReposState()
}