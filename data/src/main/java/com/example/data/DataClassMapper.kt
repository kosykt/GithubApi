package com.example.data

import com.example.data.database.model.RepoEntity
import com.example.data.database.model.UserEntity
import com.example.data.network.model.RepoDTO
import com.example.data.network.model.UserDTO
import com.example.domain.models.DomainRepoModel
import com.example.domain.models.DomainUserModel

fun List<UserDTO>.toListDomainUserModel() = this.map {
    DomainUserModel(
        id = it.id,
        login = it.login,
        repos_url = it.repos_url,
    )
}

fun List<UserDTO>.toListUserEntity() = this.map {
    UserEntity(
        id = it.id,
        login = it.login,
        repos_url = it.repos_url,
    )
}

@JvmName("toListUserEntityDomainUserModel")
fun List<DomainUserModel>.toListUserEntity() = this.map {
    UserEntity(
        id = it.id,
        login = it.login,
        repos_url = it.repos_url,
    )
}

@JvmName("toListDomainUserModelUserEntity")
fun List<UserEntity>.toListDomainUserModel() = this.map {
    DomainUserModel(
        id = it.id,
        login = it.login,
        repos_url = it.repos_url,
    )
}

fun List<RepoDTO>.toListDomainRepoModel() = this.map {
    DomainRepoModel(
        id = it.id,
        name = it.name,
        ownerId = it.owner.id
    )
}

@JvmName("toListDomainRepoModelRepoEntity")
fun List<RepoEntity>.toListDomainRepoModel() = this.map {
    DomainRepoModel(
        id = it.id,
        name = it.name,
        ownerId = it.ownerId
    )
}

fun List<RepoDTO>.toListRepoEntity() = this.map {
    RepoEntity(
        id = it.id,
        name = it.name,
        ownerId = it.owner.id
    )
}