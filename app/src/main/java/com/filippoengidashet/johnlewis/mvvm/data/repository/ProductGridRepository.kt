package com.filippoengidashet.johnlewis.mvvm.data.repository

import com.filippoengidashet.johnlewis.mvvm.data.api.ProductsApi
import com.filippoengidashet.johnlewis.mvvm.data.mapper.ProductMapper
import com.filippoengidashet.johnlewis.mvvm.data.model.ApiResult
import com.filippoengidashet.johnlewis.mvvm.data.model.entity.ProductEntity
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
            ApiResult.Success(mappedProducts)
        } catch (t: Throwable) {
            ApiResult.Error(t)
        }
    }
}
