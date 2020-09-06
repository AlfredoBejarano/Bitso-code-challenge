package me.alfredobejarano.bitsocodechallenge.view.adapter

import androidx.recyclerview.widget.DiffUtil
import me.alfredobejarano.bitsocodechallenge.model.local.Book

/**
 * BookDiffCallback
 */
class BookDiffCallback(private val oldItems: List<Book>, private val newItems: List<Book>) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition].book == newItems[newItemPosition].book

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition].areContentsTheSame(newItems[newItemPosition])
}