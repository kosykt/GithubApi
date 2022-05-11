package com.example.githubapi.data.network.model

data class RepoDTO(
    val id: String,
    val name: String,
    val owner: RepoOwner
)

data class RepoOwner(
    val id: String
)
