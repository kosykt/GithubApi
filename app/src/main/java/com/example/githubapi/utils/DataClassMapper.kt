package com.example.githubapi.utils

import com.example.githubapi.data.database.model.UserEntity
import com.example.githubapi.data.network.model.UserDTO
import com.example.githubapi.ui.usersfragment.DomainUserModel

fun List<UserDTO>.dtoToListDomainUserModel() = this.map {
    DomainUserModel(
        id = it.id,
        login = it.login,
        repos_url = it.repos_url,
    )
}

fun List<UserDTO>.dtoToListUserEntity() = this.map {
    UserEntity(
        id = it.id,
        login = it.login,
        repos_url = it.repos_url,
    )
}

fun List<UserEntity>.entityToListDomainUserModel() = this.map {
    DomainUserModel(
        id = it.id,
        login = it.login,
        repos_url = it.repos_url,
    )
}