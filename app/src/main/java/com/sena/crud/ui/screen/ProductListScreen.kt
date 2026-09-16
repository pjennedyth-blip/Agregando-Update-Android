package com.sena.crud.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.sena.crud.domain.model.ProductModel
import com.sena.crud.ui.viewModel.ProductViewModel

@Composable
fun ProductListScreen(
    navController: NavController,
    viewModel: ProductViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var searchId by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getProducts()
    }

    Scaffold(
        topBar = {
            Column {
                Text(
                    text = "Productos",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = searchId,
                        onValueChange = { searchId = it },
                        label = { Text("ID Producto") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    Button(
                        onClick = {
                            val id = searchId.toIntOrNull()
                            if (id != null) {
                                navController.navigate("product_detail/$id")
                            }
                        }
                    ) {
                        Text("BUSCAR")
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("product_create") }) {
                Text("+", modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }

            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage ?: "",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
                Button(onClick = { viewModel.getProducts() }) {
                    Text("Reintentar")
                }
            }

            if (uiState.products.isEmpty() && !uiState.isLoading && uiState.errorMessage == null) {
                Text(text = "No hay productos disponibles.")
            }

            LazyColumn {
                items(uiState.products, key = { it.id }) { product ->
                    ProductListItem(
                        product = product,
                        onDelete = { navController.navigate("product_delete/${product.id}") },
                        onEdit = { navController.navigate("product_update/${product.id}") },
                        onClick = { navController.navigate("product_detail/${product.id}") }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductListItem(
    product: ProductModel,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.title, style = MaterialTheme.typography.titleMedium)
                Text(text = "Precio: $${product.price}", style = MaterialTheme.typography.bodyMedium)
            }
            OutlinedButton(onClick = onEdit, modifier = Modifier.padding(end = 4.dp)) {
                Text("Edit")
            }
            OutlinedButton(onClick = onDelete) {
                Text("Del")
            }
        }
    }
}
