package com.sena.crud.ui.section

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sena.crud.domain.model.ProductModel
import com.sena.crud.ui.state.ProductUIState

@Composable
fun ProductDetails(
    uiState: ProductUIState,
    onRetry: () -> Unit,
    onShowAll: () -> Unit,
    onCreate: (
        String,
        String,
        String,
        Double
    ) -> Unit,
    onUpdate: (
        Int,
        String,
        String,
        String,
        Double
    ) -> Unit,
    onDelete: (Int) -> Unit
) {

    var selectedProduct by remember {
        mutableStateOf<ProductModel?>(null)
    }

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var category by remember {
        mutableStateOf("")
    }

    var price by remember {
        mutableStateOf("")
    }

    LaunchedEffect(uiState.product?.id) {

        uiState.product?.let { product ->

            selectedProduct = product

            title = product.title
            description = product.description
            category = product.category
            price = product.price.toString()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "CRUD DE PRODUCTOS"
        )

        Text(
            text = "Crear, consultar, actualizar y eliminar"
        )

        Button(
            onClick = onShowAll,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("MOSTRAR TODOS")
        }

        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Título")
            },
            singleLine = true
        )

        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Descripción")
            }
        )

        OutlinedTextField(
            value = category,
            onValueChange = {
                category = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Categoría")
            },
            singleLine = true
        )

        OutlinedTextField(
            value = price,
            onValueChange = {
                price = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Precio")
            },
            singleLine = true
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = {

                    val priceValue =
                        price.toDoubleOrNull()

                    if (
                        title.isNotBlank() &&
                        description.isNotBlank() &&
                        category.isNotBlank() &&
                        priceValue != null
                    ) {

                        onCreate(
                            title,
                            description,
                            category,
                            priceValue
                        )

                        selectedProduct = null

                        title = ""
                        description = ""
                        category = ""
                        price = ""
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = !uiState.isCreating
            ) {

                if (uiState.isCreating) {

                    CircularProgressIndicator()

                } else {

                    Text("CREAR")
                }
            }

            Button(
                onClick = {

                    val priceValue =
                        price.toDoubleOrNull()

                    val product =
                        selectedProduct

                    if (
                        product != null &&
                        priceValue != null
                    ) {

                        onUpdate(
                            product.id,
                            title,
                            description,
                            category,
                            priceValue
                        )
                    }
                },
                modifier = Modifier.weight(1f),
                enabled =
                    selectedProduct != null &&
                            !uiState.isUpdating
            ) {

                if (uiState.isUpdating) {

                    CircularProgressIndicator()

                } else {

                    Text("ACTUALIZAR")
                }
            }
        }

        if (uiState.successMessage != null) {

            Text(
                text = uiState.successMessage,
                modifier = Modifier.padding(8.dp)
            )
        }

        if (uiState.errorMessage != null) {

            Text(
                text = uiState.errorMessage,
                modifier = Modifier.padding(8.dp)
            )
        }

        Text(
            text = "Productos"
        )

        LazyColumn {

            items(
                items = uiState.products,
                key = {
                    it.id
                }
            ) { product ->

                ProductItem(
                    product = product,

                    selected =
                        selectedProduct?.id == product.id,

                    onSelect = {

                        selectedProduct = product

                        title = product.title
                        description = product.description
                        category = product.category
                        price = product.price.toString()
                    },

                    onDelete = {
                        onDelete(product.id)

                        if (
                            selectedProduct?.id ==
                            product.id
                        ) {

                            selectedProduct = null

                            title = ""
                            description = ""
                            category = ""
                            price = ""
                        }
                    }
                )
            }
        }
    }
}


@Composable
private fun ProductItem(
    product: ProductModel,
    selected: Boolean,
    onSelect: () -> Unit,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable {
                onSelect()
            }
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "ID: ${product.id}"
            )

            Text(
                text = product.title
            )

            Text(
                text = product.description
            )

            Text(
                text = "Categoría: ${product.category}"
            )

            Text(
                text = "Precio: $${product.price}"
            )

            if (selected) {

                Text(
                    text = "Producto seleccionado"
                )
            }

            OutlinedButton(
                onClick = onDelete
            ) {

                Text("ELIMINAR")
            }
        }
    }
}