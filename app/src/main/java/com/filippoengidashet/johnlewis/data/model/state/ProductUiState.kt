package com.filippoengidashet.johnlewis.data.model.state

import com.filippoengidashet.johnlewis.data.model.entity.ProductDetailEntity

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 17:34.
 */
sealed class ProductUiState {
    object Loading : ProductUiState()
    data class Error(val message: String?) : ProductUiState()
    data class Success(val product: ProductDetailEntity) : ProductUiState()
}
