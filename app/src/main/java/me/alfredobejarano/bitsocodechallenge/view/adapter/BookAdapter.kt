package me.alfredobejarano.bitsocodechallenge.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.alfredobejarano.bitsocodechallenge.databinding.ItemBookBinding
import me.alfredobejarano.bitsocodechallenge.databinding.ItemBookQuickDetailBinding
import me.alfredobejarano.bitsocodechallenge.model.local.Book

/**
 * Adapter class that handles how to display a list of [Book] objects into a RecyclerView.
 */
class BookAdapter(
    private var books: List<Book>,
    private val onBookClick: (Book) -> Unit = {},
    private val buildViewBinding: (LayoutInflater, ViewGroup) -> ViewDataBinding
) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    /**
     * Job that will get created when an update of the list is requested.
     */
    private var updateJob: Job? = null

    /**
     * Retrieves the amount of book objects to display.
     */
    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) =
        holder.bind(books[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BookViewHolder(
        buildViewBinding(LayoutInflater.from(parent.context), parent),
        onBookClick
    )

    /**
     * Executes a Coroutine job to update the items in this adapter.
     */
    fun updateList(newItems: List<Book>) {
        updateJob = GlobalScope.launch(Dispatchers.IO) {
            val diff = DiffUtil.calculateDiff(BookDiffCallback(books, newItems))
            launch(Dispatchers.Main) {
                diff.dispatchUpdatesTo(this@BookAdapter)
                books = newItems
            }
        }
    }

    /**
     * Cancels the update job (if currently active) when this adapter gets removes from it's
     * recyclerview.
     */
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        if (updateJob?.isActive == true) {
            updateJob?.cancel()
        }
        updateJob = null
    }

    /**
     * Class that represents the details of a Book in the UI.
     */
    class BookViewHolder(
        private val binding: ViewDataBinding,
        private val onBookClick: (Book) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Book) = when (binding) {
            is ItemBookBinding -> bind(binding, item)
            is ItemBookQuickDetailBinding -> bind(binding, item)
            else -> Unit
        }.also { binding.root.setOnClickListener { onBookClick(item) } }

        private fun bind(binding: ItemBookBinding, item: Book) = binding.run {
            book = item
        }

        private fun bind(binding: ItemBookQuickDetailBinding, item: Book) = binding.run {
            book = item
        }
    }
}