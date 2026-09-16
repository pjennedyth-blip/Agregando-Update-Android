package com.sena.crud.data.repository

import com.sena.crud.data.mapper.toDomain
import com.sena.crud.data.remote.api.ProductApiService
import com.sena.crud.data.remote.dto.req.product.CreateProductRequest
import com.sena.crud.data.remote.dto.req.product.UpdateProductRequest
import com.sena.crud.domain.model.ProductModel
import com.sena.crud.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApiService
) : ProductRepository {

    override suspend fun GetProductById(
        id: Int
    ): ProductModel {

        val response = api.GetProductByid(id)

        return response.toDomain()
    }

    override suspend fun GetProducts(): List<ProductModel> {

        val response = api.GetProducts()

        return response.products.map {
            it.toDomain()
        }
    }

    override suspend fun CreateProduct(
        product: CreateProductRequest
    ): ProductModel {

        val response = api.CreateProduct(product)

        return ProductModel(
            id = response.id,
            title = response.title,
            description = response.description,
            category = response.category,
            price = response.price
        )
    }

    override suspend fun UpdateProduct(
        id: Int,
        product: UpdateProductRequest
    ): ProductModel {

        val response = api.UpdateProduct(
            id = id,
            product = product
        )

        return ProductModel(
            id = response.id,
            title = response.title,
            description = response.description,
            category = response.category,
            price = response.price
        )
    }

    override suspend fun DeleteProduct(
        id: Int
    ): Boolean {

        val response = api.DeleteProduct(id)

        return response.isDeleted
    }
}