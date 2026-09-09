package com.sena.crud.ui.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
    onUpdate: (ProductModel) -> Unit
) {

    when {

        uiState.isLoading -> {

            CircularProgressIndicator()
        }

        uiState.errorMessage != null &&
                uiState.product == null -> {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),

                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = uiState.errorMessage
                )

                Button(
                    onClick = onRetry
                ) {

                    Text(
                        text = "Reintentar"
                    )
                }
            }
        }

        uiState.product != null -> {

            ProductEditForm(
                product = uiState.product,

                isUpdating = uiState.isUpdating,

                successMessage = uiState.successMessage,

                onUpdate = onUpdate
            )
        }
    }
}


@Composable
private fun ProductEditForm(
    product: ProductModel,
    isUpdating: Boolean,
    successMessage: String?,
    onUpdate: (ProductModel) -> Unit
) {

    var title by remember(product.id) {

        mutableStateOf(product.title)
    }

    var description by remember(product.id) {

        mutableStateOf(product.description)
    }

    var category by remember(product.id) {

        mutableStateOf(product.category)
    }

    var price by remember(product.id) {

        mutableStateOf(product.price.toString())
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),

        verticalArrangement =
            Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Editar producto"
        )

        Text(
            text = "ID: ${product.id}"
        )


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


        Button(
            onClick = {

                val priceValue =
                    price.toDoubleOrNull()

                if (priceValue != null) {

                    val updatedProduct =
                        product.copy(

                            title = title,

                            description = description,

                            category = category,

                            price = priceValue
                        )

                    onUpdate(updatedProduct)
                }
            },

            enabled = !isUpdating,

            modifier = Modifier.fillMaxWidth()
        ) {

            if (isUpdating) {

                CircularProgressIndicator()

            } else {

                Text(
                    text = "Actualizar producto"
                )
            }
        }


        if (successMessage != null) {

            Text(
                text = successMessage
            )
        }
    }
}