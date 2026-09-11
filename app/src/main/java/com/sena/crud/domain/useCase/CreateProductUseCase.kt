package com.sena.crud.domain.useCase

import com.sena.crud.data.remote.dto.req.product.CreateProductRequest
import com.sena.crud.domain.model.ProductModel
import com.sena.crud.domain.repository.ProductRepository
import javax.inject.Inject

class CreateProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(
        product: CreateProductRequest
    ): ProductModel {

        return repository.CreateProduct(product)
    }
}