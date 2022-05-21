package com.example.data.network

import com.example.data.network.model.RepoDTO
import com.example.data.network.model.UserDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("/users")
    suspend fun getUsers(): List<UserDTO>

    @GET("/users/{login}/repos")
    suspend fun getRepos(@Path("login") login: String): List<RepoDTO>
}