package com.filippoengidashet.johnlewis.data.repository

import com.filippoengidashet.johnlewis.data.api.ProductsApi
import com.filippoengidashet.johnlewis.data.model.entity.ProductEntity
import com.filippoengidashet.johnlewis.data.mapper.ProductMapper
import com.filippoengidashet.johnlewis.data.model.ApiResult
import com.filippoengidashet.johnlewis.data.model.ApiResult.Error
import com.filippoengidashet.johnlewis.data.model.ApiResult.Success
import javax.inject.Inject

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 13:44.
 */
class ProductGridRepository @Inject constructor(
    private val api: ProductsApi,
    private val mapper: ProductMapper,
) {
    suspend fun getProductsResult(keyword: String): ApiResult<List<ProductEntity>> {
        return try {
            val products = api.getProducts(query = keyword.trim())
            val mappedProducts = mapper.mapProducts(products)
            Success(mappedProducts)
        } catch (t: Throwable) {
            Error(t)
        }
    }
}
