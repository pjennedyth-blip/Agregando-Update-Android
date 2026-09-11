package com.sena.crud.domain.repository

import com.sena.crud.data.remote.dto.req.product.CreateProductRequest
import com.sena.crud.data.remote.dto.req.product.UpdateProductRequest
import com.sena.crud.domain.model.ProductModel

interface ProductRepository {

    suspend fun GetProductById(
        id: Int
    ): ProductModel

    suspend fun GetProducts(): List<ProductModel>

    suspend fun CreateProduct(
        product: CreateProductRequest
    ): ProductModel

    suspend fun UpdateProduct(
        id: Int,
        product: UpdateProductRequest
    ): ProductModel

    suspend fun DeleteProduct(
        id: Int
    ): Boolean
}