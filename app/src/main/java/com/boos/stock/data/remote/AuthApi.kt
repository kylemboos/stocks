package com.boos.stock.data.remote

import com.boos.stock.data.auth.AuthRequest
import com.example.data.responses.AuthResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface AuthApi {

    @POST("signin")
    suspend fun signIn(
        @Body request: AuthRequest
    ): AuthResponse

    @POST("signup")
    suspend fun signUp(
        @Body request: AuthRequest
    )

    @GET("authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String
    )
    companion object {
        const val BASE_URL = "http://192.168.1.180:8080/"
    }
}