package com.example.githubapi.ui.usersfragment

import com.example.domain.models.DomainUserModel

sealed class UsersState {

    data class Success(val response: List<DomainUserModel>) : UsersState()
    data class Error(val error: String) : UsersState()
    object Loading : UsersState()
}