package com.filippoengidashet.johnlewis.data.model.dto

import com.google.gson.JsonObject

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 12:18.
 */
data class ProductResponse(
    val title: String?,
    val media: ProductMedia?,
    val price: JsonObject?,
    val details: ProductDetails?,
    val displaySpecialOffer: String?,
    val additionalServices: AdditionalServices?,
    val code: String?,
)
