package com.sena.crud.ui.NavHost

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "products"
    ) {

        composable("products") {
            // Aquí irá la pantalla principal de productos
        }
    }
}