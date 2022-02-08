package com.filippoengidashet.johnlewis.data.mapper

import com.filippoengidashet.johnlewis.data.model.ProductSpec
import com.filippoengidashet.johnlewis.data.model.dto.ProductResponse
import com.filippoengidashet.johnlewis.data.model.dto.ProductsResponse
import com.filippoengidashet.johnlewis.data.model.entity.ProductDetailEntity
import com.filippoengidashet.johnlewis.data.model.entity.ProductEntity
import com.filippoengidashet.johnlewis.utils.Utils
import com.google.gson.JsonObject
import javax.inject.Inject

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 14:01.
 */
class ProductMapper @Inject constructor() {

    companion object {

        private const val MAX_RESULTS = 20
    }

    fun mapProduct(product: ProductResponse): ProductDetailEntity {

        val title = product.title ?: ""

        //FIXME Issue with response model for 'Phone'  - price now, it's not always String???
        val nowPrice = product.price?.get("now")
        val price = (if (nowPrice?.isJsonPrimitive == true) {
            nowPrice.asString
        } else {
            (nowPrice as JsonObject).get("from")?.asString //from/to
        }) ?: ""

        val code = product.code ?: ""
        val displaySpecialOffer = product.displaySpecialOffer ?: ""
        val additionalServices = product.additionalServices?.includedServices?.joinToString() ?: ""
        val media = product.media?.images?.urls?.map {
            it.let { url -> Utils.resolveURL(url) }
        } ?: emptyList()

        val details = product.details

        val productInformation = details?.productInformation ?: ""

        val productSpecs = details?.features?.takeIf {
            it.isNotEmpty()
        }?.get(0)?.attributes?.takeIf {
            it.isNotEmpty()
        }?.map { ProductSpec(it.name ?: "", it.value ?: "") } ?: emptyList()

        return ProductDetailEntity(
            title, media, price, displaySpecialOffer, additionalServices,
            productInformation, code, productSpecs
        )
    }

    fun mapProducts(products: ProductsResponse, max: Int = MAX_RESULTS): List<ProductEntity> {
        val productList = mutableListOf<ProductEntity>()
        products.products?.also {
            val allProducts = it.iterator()
            var counter = 0
            while (allProducts.hasNext()) {

                if (counter >= max) break

                val product = allProducts.next()

                val productId = product.productId ?: ""
                val title = product.title ?: ""

                //FIXME Issue with response model for 'Phone'  - price now, it's not always String???

                val nowPrice = product.price?.get("now")
                val price = (if (nowPrice?.isJsonPrimitive == true) {
                    nowPrice.asString
                } else {
                    (nowPrice as JsonObject).get("from")?.asString //from/to
                }) ?: ""

                val image = product.image?.let { url -> Utils.resolveURL(url) } ?: ""

                val entity = ProductEntity(productId, title, price, image)

                productList.add(entity)

                counter++
            }
        }
        return productList
    }
}
