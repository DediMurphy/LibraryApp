package com.latihan.jetperpustakaan.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object About : Screen("about")
    object Detail : Screen("home/{rewardId}") {
        fun createRoute(rewardId: Long) = "home/$rewardId"
    }
    object Cart : Screen("cart/{id}") {
        fun createRoute(id: Long) = "detail/$id"
    }
}
