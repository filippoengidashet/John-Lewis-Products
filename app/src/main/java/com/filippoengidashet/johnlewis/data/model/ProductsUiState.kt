package com.filippoengidashet.johnlewis.data.model

import com.filippoengidashet.johnlewis.data.model.entity.ProductEntity

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 14:20.
 */
sealed class ProductsUiState {
    object Loading : ProductsUiState()
    data class Error(val message: String?) : ProductsUiState()
    data class Success(val products: List<ProductEntity>) : ProductsUiState()
}
