package com.sena.crud.ui.state

import com.sena.crud.domain.model.ProductModel

data class ProductUIState(

    val isLoading: Boolean = false,

    val isCreating: Boolean = false,

    val isUpdating: Boolean = false,

    val isDeleting: Boolean = false,

    val product: ProductModel? = null,

    val products: List<ProductModel> = emptyList(),

    val errorMessage: String? = null,

    val successMessage: String? = null
)