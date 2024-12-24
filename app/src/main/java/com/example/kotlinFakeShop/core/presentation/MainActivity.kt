package com.example.kotlinFakeShop.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kotlinFakeShop.NavGraphs
import com.example.kotlinFakeShop.core.presentation.components.CustomScaffold
import com.example.kotlinFakeShop.core.presentation.ui.theme.FakeShopTheme
import com.example.kotlinFakeShop.destinations.AccountScreenDestination
import com.example.kotlinFakeShop.destinations.CartScreenDestination
import com.example.kotlinFakeShop.destinations.HomeScreenDestination
import com.example.kotlinFakeShop.destinations.WishlistScreenDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.rememberNavHostEngine
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FakeShopTheme{
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navHostEngine = rememberNavHostEngine()
                    val navController = rememberNavController()
                    val newBackStackEntry by navController.currentBackStackEntryAsState()
                    val route = newBackStackEntry?.destination?.route

                    CustomScaffold(
                        navController = navController,
                        showBottomBar = route in listOf(
                            HomeScreenDestination.route,
                            WishlistScreenDestination.route,
                            CartScreenDestination.route,
                            AccountScreenDestination.route
                        )
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            DestinationsNavHost(
                                navGraph = NavGraphs.root,
                                navController = navController,
                                engine = navHostEngine
                            )
                        }
                    }
                }
            }
        }
    }
}