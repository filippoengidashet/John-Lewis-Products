package com.filippoengidashet.johnlewis.mvvm.data.api

import com.filippoengidashet.johnlewis.mvvm.data.config.AppConstants
import com.filippoengidashet.johnlewis.mvvm.data.model.dto.ProductResponse
import com.filippoengidashet.johnlewis.mvvm.data.model.dto.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 12:09.
 */
interface ProductsApi {

    @Headers(
        "User-Agent: Mozilla/5.0 (Linux; Android 11; SM-T500 Build/RP1A.200720.012; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/87.0.4280.141 Safari/537.36"
    )
    @GET(AppConstants.Api.ENDPOINT_PRODUCT_SEARCH)
    suspend fun getProducts(
        @Query("key") key: String = AppConstants.Api.API_KEY,
        @Query("q") query: String
    ): ProductsResponse

    @Headers(
        "User-Agent: Mozilla/5.0 (Linux; Android 11; SM-T500 Build/RP1A.200720.012; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/87.0.4280.141 Safari/537.36"
    )
    @GET(AppConstants.Api.ENDPOINT_PRODUCT_DETAIL)
    suspend fun getProduct(@Path("productId") productId: String): ProductResponse
}
