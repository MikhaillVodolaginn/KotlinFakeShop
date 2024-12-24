package com.example.kotlinFakeShop.feature_products.domain.model

import com.example.kotlinFakeShop.R
import com.example.kotlinFakeShop.destinations.*

sealed class BottomNavItem(var icon: Int, var destination: Destination) {
    object Home : BottomNavItem(
        icon = R.drawable.ic_home,
        destination = HomeScreenDestination
    )

    object Wishlist : BottomNavItem(
        icon = R.drawable.ic_heart,
        destination = WishlistScreenDestination
    )

    object Cart : BottomNavItem(
        icon = R.drawable.ic_basket,
        destination = CartScreenDestination
    )

    object Account : BottomNavItem(
        icon = R.drawable.ic_user,
        destination = AccountScreenDestination
    )
}
