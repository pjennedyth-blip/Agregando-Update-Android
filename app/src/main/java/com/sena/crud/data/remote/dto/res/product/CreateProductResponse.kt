package com.sena.crud.data.remote.dto.res.product

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateProductResponse(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double
)