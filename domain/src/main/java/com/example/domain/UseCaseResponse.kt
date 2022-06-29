package com.example.domain

sealed class UseCaseResponse {
    data class Success<out T>(val data: T) : UseCaseResponse()
    data class Error(val message: String) : UseCaseResponse()
}
