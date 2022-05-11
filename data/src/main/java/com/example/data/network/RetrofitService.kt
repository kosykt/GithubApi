package com.example.data.network

import com.example.data.network.model.RepoDTO
import com.example.data.network.model.UserDTO
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitService {

    @GET("/users")
    suspend fun getUsers(): List<UserDTO>

    @GET
    suspend fun getRepos(@Url url: String): List<RepoDTO>
}