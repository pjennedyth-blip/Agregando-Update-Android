package com.sena.crud.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sena.crud.domain.model.ProductModel

@Composable
fun productCard(
    product: ProductModel
) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp),

            verticalArrangement =
                Arrangement.spacedBy(10.dp)
        ) {

            Text(
                text = "Producto #${product.id}",

                style =
                    MaterialTheme.typography.headlineSmall,

                fontWeight =
                    FontWeight.Bold
            )

            Text(
                text = product.title,

                style =
                    MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Categoría: ${product.category}",

                style =
                    MaterialTheme.typography.bodyMedium
            )

            Text(
                text = product.description,

                style =
                    MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Precio: $${product.price}",

                style =
                    MaterialTheme.typography.labelLarge
            )
        }
    }
}