package com.example.kotlinFakeShop.feature_auth.presentation.auth_dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.kotlinFakeShop.R
import com.example.kotlinFakeShop.core.presentation.ui.theme.PrimaryYellow
import com.example.kotlinFakeShop.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.system.exitProcess

@OptIn(ExperimentalComposeUiApi::class)
@Destination
@Composable
fun AuthDashboardScreen(
    navigator: DestinationsNavigator
) {
    Dialog(
        onDismissRequest = {
            exitProcess(0)
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopStart
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.banner_image)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 24.dp),
                        color = PrimaryYellow,
                        fontSize = 24.sp,
                        text = "Вперед, к прекрасным покупкам!"
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp),
                        onClick = {
                            navigator.navigate(LoginScreenDestination)
                        },
                        shape = RoundedCornerShape(8)

                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            text = "Войти",
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}