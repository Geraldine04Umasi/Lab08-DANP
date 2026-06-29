package com.example.gamecatalog.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gamecatalog.presentation.ui.GameDetailScreen
import com.example.gamecatalog.presentation.ui.GameScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "games"
    ) {
        composable("games") {
            GameScreen(
                onGameClick = { gameId ->
                    navController.navigate("detail/$gameId")
                }
            )
        }

        composable(
            route = "detail/{gameId}",
            arguments = listOf(
                navArgument("gameId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getInt("gameId") ?: 0
            GameDetailScreen(
                gameId = gameId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
