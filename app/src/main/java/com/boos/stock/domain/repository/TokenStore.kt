package com.boos.stock.domain.repository

interface TokenStore {
    suspend fun getToken(): String?
    suspend fun saveToken(token: String)
}