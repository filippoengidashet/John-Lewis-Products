package com.filippoengidashet.johnlewis.data.model.entity

import com.filippoengidashet.johnlewis.data.model.ProductSpec

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 17:35.
 */
data class ProductDetailEntity constructor(
    val title: String,
    val media: List<String>,
    val price: String,
    val displaySpecialOffer: String,
    val additionalServices: String,
    val productInformation: String,
    val code: String,
    val productSpecs: List<ProductSpec>
)
