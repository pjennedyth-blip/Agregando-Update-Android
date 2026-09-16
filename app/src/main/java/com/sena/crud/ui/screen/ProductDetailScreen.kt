package com.sena.crud.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.sena.crud.ui.component.productCard
import com.sena.crud.ui.viewModel.ProductViewModel

@Composable
fun ProductDetailScreen(
    productId: Int,
    navController: NavController,
    viewModel: ProductViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(productId) {
        viewModel.getProductById(productId)
    }

    Scaffold(
        topBar = {
            Text(
                text = "Detalle del Producto",
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
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else if (uiState.product != null) {
                val product = uiState.product!!
                
                productCard(product = product)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { navController.navigate("product_update/${product.id}") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("EDITAR")
                    }
                    OutlinedButton(
                        onClick = { 
                            navController.navigate("product_delete/${product.id}")
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("ELIMINAR")
                    }
                }
            } else if (uiState.errorMessage != null) {
                Text(text = uiState.errorMessage ?: "Error desconocido")
            }
        }
    }
}
