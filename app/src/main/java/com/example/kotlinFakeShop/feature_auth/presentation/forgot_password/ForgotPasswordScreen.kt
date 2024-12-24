package com.example.kotlinFakeShop.feature_auth.presentation.forgot_password

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun ForgotPasswordScreen() {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = "Забыли пароль?",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Пожалуйста, введите email, по которому вы регистрировались, мы отправим туда пароль",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    ) {
        ForgotPasswordScreenContent()
    }
}

@Composable
private fun ForgotPasswordScreenContent() {
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        item {
            Spacer(modifier = Modifier.height(64.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = true,
                    keyboardType = KeyboardType.Email
                ),
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {},
                shape = RoundedCornerShape(8)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp), text = "Continue", textAlign = TextAlign.Center
                )
            }
        }
    }
}