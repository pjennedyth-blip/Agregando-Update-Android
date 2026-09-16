package com.sena.crud.data.mapper

import com.sena.crud.data.remote.dto.req.product.Product
import com.sena.crud.domain.model.ProductModel

fun Product.toDomain(): ProductModel {

    return ProductModel(

        id = id,

        title = title ?: "Sin título",

        description = description ?: "Sin descripción",

        category = category ?: "Sin categoría",

        price = price ?: 0.0
    )
}