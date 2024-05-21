package com.latihan.jetperpustakaan

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.latihan.jetperpustakaan.ui.navigation.NavigationItem
import com.latihan.jetperpustakaan.ui.navigation.Screen
import com.latihan.jetperpustakaan.ui.screen.AboutScreen
import com.latihan.jetperpustakaan.ui.screen.cart.SaveBook
import com.latihan.jetperpustakaan.ui.screen.detail.DetailScreen
import com.latihan.jetperpustakaan.ui.screen.home.HomeScreen
import com.latihan.jetperpustakaan.ui.theme.JetPerpustakaanTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetBookApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) {innerPadding ->
        val argumentId = "rewardId"

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { bookId ->
                        navController.navigate(Screen.Detail.createRoute(bookId))
                    }
                )
            }
            composable(Screen.About.route) {
                AboutScreen(navigateBack = {
                    navController.navigateUp()
                })
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument(argumentId) { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong(argumentId) ?: -1L
                DetailScreen(
                    bookId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToSave = {
                        navController.navigate(Screen.Cart.createRoute(id))
                    }
                )
            }
            composable(
                route = Screen.Cart.route,
                arguments = listOf(navArgument("id"){ type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("id") ?: -1L
                SaveBook(bookId = id)
            }
        }

    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.save_page),
                icon = Icons.Default.Star,
                screen = Screen.Cart
            ),
            NavigationItem(
                title = stringResource(R.string.about_page),
                icon = Icons.Default.AccountCircle,
                screen = Screen.About
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title)},
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route){
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview() {
    JetPerpustakaanTheme() {
        JetBookApp()
    }
}