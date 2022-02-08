package com.filippoengidashet.johnlewis.mvvm.data.model.dto

import com.google.gson.JsonObject

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 13:27.
 */
class Product(
    val productId: String?,
    val title: String?,
    val image: String?,
    val price: JsonObject?,
)
