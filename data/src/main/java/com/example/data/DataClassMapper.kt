package com.example.data

import com.example.data.database.model.FavouriteReposEntity
import com.example.data.database.model.FavouriteUserEntity
import com.example.data.database.model.HistoryCacheRepoEntity
import com.example.data.database.model.HistoryCacheUserEntity
import com.example.data.network.model.RepoDTO
import com.example.data.network.model.UserDTO
import com.example.domain.models.DomainRepoModel
import com.example.domain.models.DomainUserModel

fun List<UserDTO>.toListDomainUserModel() = this.map {
    DomainUserModel(
        id = it.id,
        login = it.login,
        reposUrl = it.reposUrl,
        avatarUrl = it.avatarUrl,
    )
}

fun List<UserDTO>.toListUserEntity() = this.map {
    HistoryCacheUserEntity(
        id = it.id,
        login = it.login,
        reposUrl = it.reposUrl,
        avatarUrl = it.avatarUrl,
    )
}

@JvmName("toListDomainUserModelUserEntity")
fun List<HistoryCacheUserEntity>.toListDomainUserModel() = this.map {
    DomainUserModel(
        id = it.id,
        login = it.login,
        reposUrl = it.reposUrl,
        avatarUrl = it.avatarUrl,
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
fun List<HistoryCacheRepoEntity>.toListDomainRepoModel() = this.map {
    DomainRepoModel(
        id = it.id,
        name = it.name,
        ownerId = it.ownerId
    )
}

fun List<RepoDTO>.toListRepoEntity() = this.map {
    HistoryCacheRepoEntity(
        id = it.id,
        name = it.name,
        ownerId = it.owner.id
    )
}

fun DomainUserModel.toFavouriteUserEntity() = FavouriteUserEntity(
    id = this.id
)

fun List<FavouriteUserEntity>.toListString() = this.map {
    it.id
}

fun DomainRepoModel.toFavouriteReposEntity() = FavouriteReposEntity(
    id = this.id
)

@JvmName("toListStringFavouriteReposEntity")
fun List<FavouriteReposEntity>.toListString() = this.map {
    it.id
}