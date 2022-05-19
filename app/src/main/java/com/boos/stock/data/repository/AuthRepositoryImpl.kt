package com.boos.stock.data.repository

import com.boos.stock.data.auth.AuthRequest
import com.boos.stock.data.remote.AuthApi
import com.boos.stock.domain.repository.AuthRepository
import com.boos.stock.domain.repository.TokenStore
import com.boos.stock.util.Resource
import com.example.data.responses.AuthResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenStore: TokenStore
): AuthRepository {

    override suspend fun signIn(username: String, password: String): Resource<AuthResponse> {
        return try {
            val response = authApi.signIn(AuthRequest(username, password))
            tokenStore.saveToken(response.token)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(message = "Error authenticating")
        }
    }

    override suspend fun signUp(username: String, password: String): Resource<Unit> {
        return try {
            authApi.signUp(AuthRequest(username, password))
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(message = "Error signing up")
        }
    }

    override suspend fun authenticate(): Resource<Unit> {
        return try {
            val token = tokenStore.getToken() ?: return Resource.Error(message = "Error authenticating")
            authApi.authenticate("Bearer $token")
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(message = "Error authenticating")
        }
    }

}