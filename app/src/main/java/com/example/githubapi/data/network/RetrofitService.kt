package com.example.githubapi.data.network

import com.example.githubapi.data.network.model.UserDTO
import retrofit2.http.GET

interface RetrofitService {

    @GET("/users")
    suspend fun getUsers(): List<UserDTO>
}