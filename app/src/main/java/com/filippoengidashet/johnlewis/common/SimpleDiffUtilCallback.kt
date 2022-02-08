package com.filippoengidashet.johnlewis.common

import androidx.recyclerview.widget.DiffUtil

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 15:06.
 */
abstract class SimpleDiffUtilCallback<T>(
    private val oldList: List<T>, private val newList: List<T>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return if (oldItem != null && newItem != null) {
            isSameItem(oldItem, newItem)
        } else {
            oldItem == null && newItem == null
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return if (oldItem != null && newItem != null) {
            isSameContent(oldItem, newItem)
        } else {
            oldItem == null && newItem == null
        }
    }

    protected open fun isSameContent(oldItem: T, newItem: T): Boolean = isSameItem(oldItem, newItem)

    abstract fun isSameItem(oldItem: T, newItem: T): Boolean
}
