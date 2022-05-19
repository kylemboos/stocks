package com.boos.stock.ui.auth.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boos.stock.domain.repository.AuthRepository
import com.boos.stock.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    //how to provide lambda for succesfull authentication directly to viewMOdel?
    //or to trigger we have an event stream for triggering the lambda in the UI.
    private val authRepository: AuthRepository
) : ViewModel() {

    var authUiState by mutableStateOf(AuthUiState())

    private val uiResultChannel = Channel<AuthResult>()
    val uiResults = uiResultChannel.receiveAsFlow()


    init {
        authenticate()
    }

    fun onEvent(uiEvent: AuthUiEvent) {
        when (uiEvent) {
            is AuthUiEvent.SignIn -> signIn()
            is AuthUiEvent.SignUp -> signUp()
            is AuthUiEvent.PasswordChanged -> {
                authUiState = authUiState.copy(password = uiEvent.password)
            }
            is AuthUiEvent.UsernameChanged -> {
                authUiState = authUiState.copy(username = uiEvent.username)
            }
        }
    }

    private fun authenticate() {
        viewModelScope.launch {
            authUiState = authUiState.copy(isLoading = true)
            when (authRepository.authenticate()) {
                is Resource.Error -> {
                    uiResultChannel.send(AuthResult.Failed)
                }
                is Resource.Loading -> {
                    // loading results not applicaple in authRepo
                }
                is Resource.Success -> {
                    uiResultChannel.send(AuthResult.Success)
                }
            }
            authUiState = authUiState.copy(isLoading = false)
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            authUiState = authUiState.copy(isLoading = true)
            val response = authRepository.signIn(authUiState.username, authUiState.password)
            when (response) {
                is Resource.Error -> {
                    uiResultChannel.send(AuthResult.Failed)
                }
                is Resource.Loading -> {
                    // loading results not applicaple in authRepo
                }
                is Resource.Success -> {
                    uiResultChannel.send(AuthResult.Success)
                }
            }
            authUiState = authUiState.copy(isLoading = false)
        }
    }


    private fun signUp() {
        viewModelScope.launch {
            authUiState = authUiState.copy(isLoading = true)
            val response = authRepository.signUp(authUiState.username, authUiState.password)
            when (response) {
                is Resource.Error -> {
                    uiResultChannel.send(AuthResult.Failed)
                }
                is Resource.Loading -> {
                    // loading results not applicaple in authRepo
                }
                is Resource.Success -> {
                    uiResultChannel.send(AuthResult.SignUp)
                }
            }
            authUiState = authUiState.copy(isLoading = false)
        }
    }
}