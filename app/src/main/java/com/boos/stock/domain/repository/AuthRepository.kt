package com.boos.stock.domain.repository

import com.boos.stock.util.Resource
import com.example.data.responses.AuthResponse

interface AuthRepository {
    suspend fun signIn(username: String, password: String): Resource<AuthResponse>
    suspend fun signUp(username: String, password: String): Resource<Unit>
    suspend fun authenticate(): Resource<Unit>
}