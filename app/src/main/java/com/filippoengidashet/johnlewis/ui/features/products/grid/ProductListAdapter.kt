package com.filippoengidashet.johnlewis.ui.features.products.grid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.filippoengidashet.johnlewis.R
import com.filippoengidashet.johnlewis.common.ImageLoader
import com.filippoengidashet.johnlewis.common.SimpleDiffUtilCallback
import com.filippoengidashet.johnlewis.data.model.entity.ProductEntity

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 14:50.
 */
class ProductListAdapter(
    private val layoutInflater: LayoutInflater,
    private val imageLoader: ImageLoader,
    private val onClick: (ProductEntity) -> Unit
) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    private val productList = mutableListOf<ProductEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            layoutInflater.inflate(R.layout.product_list_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount() = productList.size

    fun setItems(products: List<ProductEntity>) {
        val oldList = ArrayList(productList)
        productList.clear()
        productList.addAll(products)
        val diffCallback = object : SimpleDiffUtilCallback<ProductEntity>(oldList, products) {

            override fun isSameItem(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
                return oldItem == newItem
            }
        }
        DiffUtil.calculateDiff(diffCallback).dispatchUpdatesTo(this)
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textTitle = itemView.findViewById<TextView>(R.id.text_title)
        private val textPrice = itemView.findViewById<TextView>(R.id.text_price)
        private val imageThumb = itemView.findViewById<ImageView>(R.id.image_thumb)

        init {
            itemView.setOnClickListener {
                onClick(productList[adapterPosition])
            }
        }

        fun bind(entity: ProductEntity) {
            with(entity) {
                textTitle.text = title
                textPrice.text = "£$price"
                imageLoader.load(entity.imageURL, imageThumb)
            }
        }
    }
}
