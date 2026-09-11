package com.sena.crud.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sena.crud.data.remote.dto.req.product.CreateProductRequest
import com.sena.crud.data.remote.dto.req.product.UpdateProductRequest
import com.sena.crud.domain.useCase.CreateProductUseCase
import com.sena.crud.domain.useCase.DeleteProductUseCase
import com.sena.crud.domain.useCase.GetProductUseCase
import com.sena.crud.domain.useCase.GetProductsUseCase
import com.sena.crud.domain.useCase.UpdateProductUseCase
import com.sena.crud.ui.state.ProductUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUIState())

    val uiState: StateFlow<ProductUIState> =
        _uiState.asStateFlow()


    // ==========================================
    // OBTENER UN PRODUCTO
    // ==========================================

    fun getProductById(id: Int) {

        viewModelScope.launch {

            _uiState.update {

                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {

                val result = getProductUseCase(id)

                _uiState.update {

                    it.copy(
                        isLoading = false,
                        product = result,
                        errorMessage = null
                    )
                }

            } catch (e: Exception) {

                _uiState.update {

                    it.copy(
                        isLoading = false,
                        product = null,
                        errorMessage =
                            e.message
                                ?: "Error al cargar el producto"
                    )
                }
            }
        }
    }


    // ==========================================
    // MOSTRAR TODOS LOS PRODUCTOS
    // ==========================================

    fun getProducts() {

        viewModelScope.launch {

            _uiState.update {

                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {

                val result = getProductsUseCase()

                _uiState.update {

                    it.copy(
                        isLoading = false,
                        products = result,
                        errorMessage = null
                    )
                }

            } catch (e: Exception) {

                _uiState.update {

                    it.copy(
                        isLoading = false,
                        errorMessage =
                            e.message
                                ?: "Error al cargar los productos"
                    )
                }
            }
        }
    }


    // ==========================================
    // CREAR PRODUCTO
    // ==========================================

    fun createProduct(
        title: String,
        description: String,
        category: String,
        price: Double
    ) {

        viewModelScope.launch {

            _uiState.update {

                it.copy(
                    isCreating = true,
                    errorMessage = null,
                    successMessage = null
                )
            }

            try {

                val request = CreateProductRequest(
                    title = title,
                    description = description,
                    category = category,
                    price = price
                )

                val result =
                    createProductUseCase(request)

                _uiState.update {

                    it.copy(
                        isCreating = false,
                        product = result,
                        successMessage =
                            "Producto creado correctamente",
                        errorMessage = null
                    )
                }

                // Actualizamos la lista
                getProducts()

            } catch (e: Exception) {

                _uiState.update {

                    it.copy(
                        isCreating = false,
                        errorMessage =
                            e.message
                                ?: "Error al crear el producto"
                    )
                }
            }
        }
    }


    // ==========================================
    // ACTUALIZAR PRODUCTO
    // ==========================================

    fun updateProduct(
        id: Int,
        title: String,
        description: String,
        category: String,
        price: Double
    ) {

        viewModelScope.launch {

            _uiState.update {

                it.copy(
                    isUpdating = true,
                    errorMessage = null,
                    successMessage = null
                )
            }

            try {

                val request = UpdateProductRequest(
                    title = title,
                    description = description,
                    category = category,
                    price = price
                )

                val result =
                    updateProductUseCase(
                        id = id,
                        product = request
                    )

                _uiState.update {

                    it.copy(
                        isUpdating = false,
                        product = result,
                        successMessage =
                            "Producto actualizado correctamente",
                        errorMessage = null
                    )
                }

                // Actualizamos la lista
                getProducts()

            } catch (e: Exception) {

                _uiState.update {

                    it.copy(
                        isUpdating = false,
                        errorMessage =
                            e.message
                                ?: "Error al actualizar el producto"
                    )
                }
            }
        }
    }


    // ==========================================
    // ELIMINAR PRODUCTO
    // ==========================================

    fun deleteProduct(
        id: Int
    ) {

        viewModelScope.launch {

            _uiState.update {

                it.copy(
                    isDeleting = true,
                    errorMessage = null,
                    successMessage = null
                )
            }

            try {

                val deleted =
                    deleteProductUseCase(id)

                if (deleted) {

                    _uiState.update {

                        it.copy(
                            isDeleting = false,
                            product = null,
                            successMessage =
                                "Producto eliminado correctamente"
                        )
                    }

                    // Volvemos a cargar todos
                    getProducts()

                } else {

                    _uiState.update {

                        it.copy(
                            isDeleting = false,
                            errorMessage =
                                "No se pudo eliminar el producto"
                        )
                    }
                }

            } catch (e: Exception) {

                _uiState.update {

                    it.copy(
                        isDeleting = false,
                        errorMessage =
                            e.message
                                ?: "Error al eliminar el producto"
                    )
                }
            }
        }
    }
}