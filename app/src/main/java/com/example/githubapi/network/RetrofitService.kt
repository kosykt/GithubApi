package com.example.githubapi.network

import com.example.githubapi.model.UsersDTO
import retrofit2.http.GET

interface RetrofitService {

    @GET("/users")
    suspend fun getUsers(): List<UsersDTO>
}