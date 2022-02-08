package com.filippoengidashet.johnlewis.data.repository

import com.filippoengidashet.johnlewis.data.api.ProductsApi
import com.filippoengidashet.johnlewis.data.model.entity.ProductDetailEntity
import com.filippoengidashet.johnlewis.data.mapper.ProductMapper
import com.filippoengidashet.johnlewis.data.model.ApiResult
import javax.inject.Inject

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 17:29.
 */
class ProductDetailRepository @Inject constructor(
    private val api: ProductsApi,
    private val mapper: ProductMapper,
) {
    suspend fun getProductResult(productId: String): ApiResult<ProductDetailEntity> {
        return try {
            val product = api.getProduct(productId = productId)
            val mappedProduct = mapper.mapProduct(product)
            ApiResult.Success(mappedProduct)
        } catch (t: Throwable) {
            ApiResult.Error(t)
        }
    }
}
