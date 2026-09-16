package com.sena.crud.ui.NavHost

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sena.crud.ui.screen.ProductCreateScreen
import com.sena.crud.ui.screen.ProductDeleteScreen
import com.sena.crud.ui.screen.ProductDetailScreen
import com.sena.crud.ui.screen.ProductListScreen
import com.sena.crud.ui.screen.ProductUpdateScreen
import com.sena.crud.ui.viewModel.ProductViewModel

@Composable
fun AppNavHost() {

    val navController = rememberNavController()
    val viewModel: ProductViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "product_list"
    ) {

        composable("product_list") {
            ProductListScreen(navController, viewModel)
        }

        composable(
            route = "product_detail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailScreen(productId, navController, viewModel)
        }

        composable("product_create") {
            ProductCreateScreen(navController, viewModel)
        }

        composable(
            route = "product_update/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductUpdateScreen(productId, navController, viewModel)
        }

        composable(
            route = "product_delete/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDeleteScreen(productId, navController, viewModel)
        }
    }
}
