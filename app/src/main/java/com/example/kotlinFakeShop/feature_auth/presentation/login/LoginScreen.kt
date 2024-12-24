package com.example.kotlinFakeShop.feature_auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kotlinFakeShop.core.domain.model.TextFieldState
import com.example.kotlinFakeShop.core.util.UiEvents
import com.example.kotlinFakeShop.destinations.ForgotPasswordScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class)
@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val usernameState = viewModel.usernameState.value
    val passwordState = viewModel.passwordState.value
    val rememberMeState = viewModel.rememberMeState.value

    val loginState = viewModel.loginState.value
    val scaffoldState = rememberScaffoldState()

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
                is UiEvents.NavigateEvent -> {
                    navigator.navigate(
                        event.route
                    )
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Вы вошли в аккаунт!",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Column(
                Modifier.padding(16.dp), verticalArrangement = Arrangement.Top
            ) {
                Text(text = "Welcome Back", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "Login to your account to continue shopping",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        LoginScreenContent(
            usernameState = usernameState,
            passwordState = passwordState,
            rememberMeState = rememberMeState,
            loginState = loginState,
            onUserNameTextChange = {
                viewModel.setUsername(it)
            },
            onPasswordTextChange = {
                viewModel.setPassword(it)
            },
            onRememberMeClicked = {
                viewModel.setRememberMe(it)
            },
            onClickForgotPassword = {
                navigator.navigate(ForgotPasswordScreenDestination)
            },
            onClickSignIn = {
                keyboardController?.hide()
                viewModel.loginUser()
            }
        )
    }
}

@Composable
private fun LoginScreenContent(
    usernameState: TextFieldState,
    passwordState: TextFieldState,
    rememberMeState: Boolean,
    loginState: LoginState,
    onUserNameTextChange: (String) -> Unit,
    onPasswordTextChange: (String) -> Unit,
    onRememberMeClicked: (Boolean) -> Unit,
    onClickForgotPassword: () -> Unit,
    onClickSignIn: () -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
    ) {
        item {
            Spacer(modifier = Modifier.height(64.dp))

            Column {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = usernameState.text,
                    onValueChange = {
                        onUserNameTextChange(it)
                    },
                    label = {
                        Text(text = "Логин")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                    ),
                    maxLines = 1,
                    singleLine = true,
                    isError = usernameState.error != null,
                )
                if (usernameState.error != "") {
                    Text(
                        text = usernameState.error ?: "",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            Column {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = passwordState.text,
                    onValueChange = {
                        onPasswordTextChange(it)
                    },
                    label = {
                        Text(text = "Пароль")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                    ),
                    maxLines = 1,
                    singleLine = true,
                    isError = passwordState.error != null

                )
                if (passwordState.error != "") {
                    Text(
                        text = passwordState.error ?: "",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = rememberMeState, onCheckedChange = {
                        onRememberMeClicked(it)
                    })
                    Text(text = "Не выходить из аккаунта", fontSize = 12.sp)
                }
                TextButton(onClick = onClickForgotPassword) {
                    Text(text = "Забыли пароль?")
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onClickSignIn,
                shape = RoundedCornerShape(8),
                enabled = !loginState.isLoading
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp), text = "Войти", textAlign = TextAlign.Center
                )
            }
        }

        item {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (loginState.isLoading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}