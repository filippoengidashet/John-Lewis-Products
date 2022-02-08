package com.filippoengidashet.johnlewis.mvvm.ui.features.products.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.filippoengidashet.johnlewis.R
import com.filippoengidashet.johnlewis.common.ImageLoader

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 17:07.
 */
class ImagePagerAdapter constructor(
    private val imageLoader: ImageLoader
) : PagerAdapter() {

    private val itemList: MutableList<String> = mutableListOf()
    private val cache: MutableMap<Int, ImageViewHolder> = mutableMapOf()

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val viewHolder = cache[position] ?: ImageViewHolder(
            LayoutInflater.from(container.context).inflate(
                R.layout.product_pager_item_layout, container, false
            )
        ).also { cache[position] = it }
        viewHolder.bind(itemList[position])
        val view: View = viewHolder.itemView
        container.addView(view)
        return view
    }

    override fun getCount() = itemList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun setItems(items: List<String>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    inner class ImageViewHolder(val itemView: View) {

        private val imageThumb = itemView.findViewById<ImageView>(R.id.image_thumb)

        fun bind(url: String) {
            imageLoader.load(url, imageThumb)
        }
    }
}
