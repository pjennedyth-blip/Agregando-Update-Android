package com.sena.crud.data.remote.dto.res.product

import com.sena.crud.data.remote.dto.req.product.Product
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetProductsResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)