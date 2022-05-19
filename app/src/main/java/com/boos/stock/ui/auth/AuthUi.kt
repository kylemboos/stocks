package com.boos.stock.ui.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boos.stock.R
import com.boos.stock.ui.auth.presentation.AuthResult
import com.boos.stock.ui.auth.presentation.AuthUiState
import com.boos.stock.ui.theme.StocksTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun AuthUi(
    state: AuthUiState = AuthUiState(),
    onUsernameChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onSignInClicked: () -> Unit = {},
    onSignUpClicked: () -> Unit = {},
    onAuthSuccess: () -> Unit = {},
    uiResults: Flow<AuthResult> = emptyFlow(),
    ) {
    StocksTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(MaterialTheme.colors.surface),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            }

            OutlinedTextField(
                value = state.username,
                placeholder = { Text(stringResource(R.string.username)) },
                onValueChange = { onUsernameChange(it) },
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = state.password,
                placeholder = { Text(stringResource(R.string.password)) },
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { onPasswordChange(it) }
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.padding(4.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = onSignInClicked) {
                    Text(stringResource(R.string.signIn))
                }
                Spacer(Modifier.width(8.dp))
                Button(onClick = onSignUpClicked) {
                    Text(stringResource(R.string.signUp))
                }
            }
        }

        val context = LocalContext.current
        LaunchedEffect(context) {
            uiResults.collect { result ->
                when (result) {
                    is AuthResult.Success -> {
                        onAuthSuccess()
                    }
                    is AuthResult.SignUp -> {
                        Toast.makeText(
                            context,
                            "Sign Up Complete",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is AuthResult.Failed -> {
                        Toast.makeText(
                            context,
                            "Auth failed or an unknown error occured",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        //if there is an error display a message
        //if there is a success then navigate
    }
}

@Preview
@Composable
fun AuthUiPreviewLoading() {
    AuthUi(
        state = AuthUiState(isLoading = true),
    )
}

@Preview
@Composable
fun AuthUiPreviewInitial() {
    AuthUi(
        state = AuthUiState()
    )
}
