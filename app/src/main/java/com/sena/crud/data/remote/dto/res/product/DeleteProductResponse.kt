package com.sena.crud.data.remote.dto.res.product

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeleteProductResponse(
    val id: Int,
    val title: String,
    val isDeleted: Boolean
)