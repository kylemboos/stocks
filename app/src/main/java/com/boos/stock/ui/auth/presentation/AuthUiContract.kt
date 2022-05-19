package com.boos.stock.ui.auth.presentation

data class AuthUiState(
    val isLoading: Boolean = false,
    val username: String = "",
    val password: String = ""
)

sealed class AuthUiEvent{
    object SignIn: AuthUiEvent()
    object SignUp: AuthUiEvent()
    data class UsernameChanged(val username: String): AuthUiEvent()
    data class PasswordChanged(val password: String): AuthUiEvent()
}

sealed class AuthResult {
    object SignUp: AuthResult()
    object Success: AuthResult()
    object Failed: AuthResult()
}