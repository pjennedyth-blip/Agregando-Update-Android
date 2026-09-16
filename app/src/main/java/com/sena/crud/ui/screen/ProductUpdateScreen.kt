package com.sena.crud.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.sena.crud.ui.viewModel.ProductViewModel

@Composable
fun ProductUpdateScreen(
    productId: Int,
    navController: NavController,
    viewModel: ProductViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    LaunchedEffect(productId) {
        viewModel.getProductById(productId)
    }

    LaunchedEffect(uiState.product) {
        uiState.product?.let { product ->
            title = product.title
            description = product.description
            category = product.category
            price = product.price.toString()
        }
    }

    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage == "Producto actualizado correctamente") {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            Text(
                text = "Actualizar Producto",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Categoría") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val priceValue = price.toDoubleOrNull() ?: 0.0
                    viewModel.updateProduct(productId, title, description, category, priceValue)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                enabled = !uiState.isUpdating
            ) {
                if (uiState.isUpdating) {
                    CircularProgressIndicator()
                } else {
                    Text("ACTUALIZAR")
                }
            }

            if (uiState.errorMessage != null) {
                Text(text = uiState.errorMessage ?: "", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
