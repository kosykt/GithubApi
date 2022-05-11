package com.example.githubapi.data.network

import com.example.githubapi.data.network.model.RepoDTO
import com.example.githubapi.data.network.model.UserDTO
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitService {

    @GET("/users")
    suspend fun getUsers(): List<UserDTO>

    @GET
    suspend fun getRepos(@Url url: String): List<RepoDTO>
}