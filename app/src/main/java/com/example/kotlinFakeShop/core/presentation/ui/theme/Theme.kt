package com.example.kotlinFakeShop.core.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkTheme = darkColors(
    primary = PrimaryYellow,
    primaryVariant = Light,
    secondary = Gray
)

private val LightTheme = lightColors(
    primary = Dark,
    primaryVariant = Light,
    secondary = Gray
)

@Composable
fun FakeShopTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val theme = if (darkTheme) DarkTheme else LightTheme

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = if (darkTheme) Dark else Light
        )
    }

    MaterialTheme(
        colors = theme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}