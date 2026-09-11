package com.sena.crud.data.remote.dto.req.product

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateProductRequest(
    val title: String,
    val description: String,
    val category: String,
    val price: Double
)