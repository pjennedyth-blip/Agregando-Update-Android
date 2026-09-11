package com.sena.crud.data.remote.api

import com.sena.crud.data.remote.dto.req.product.CreateProductRequest
import com.sena.crud.data.remote.dto.req.product.Product
import com.sena.crud.data.remote.dto.req.product.UpdateProductRequest
import com.sena.crud.data.remote.dto.res.product.CreateProductResponse
import com.sena.crud.data.remote.dto.res.product.DeleteProductResponse
import com.sena.crud.data.remote.dto.res.product.GetProductsResponse
import com.sena.crud.data.remote.dto.res.product.UpdateProductResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApiService {

    @GET("products/{id}")
    suspend fun GetProductByid(
        @Path("id") id: Int
    ): Product

    @GET("products")
    suspend fun GetProducts(): GetProductsResponse

    @POST("products/add")
    suspend fun CreateProduct(
        @Body product: CreateProductRequest
    ): CreateProductResponse

    @PUT("products/{id}")
    suspend fun UpdateProduct(
        @Path("id") id: Int,
        @Body product: UpdateProductRequest
    ): UpdateProductResponse

    @DELETE("products/{id}")
    suspend fun DeleteProduct(
        @Path("id") id: Int
    ): DeleteProductResponse
}