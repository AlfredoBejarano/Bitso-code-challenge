package me.alfredobejarano.bitsocodechallenge.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.alfredobejarano.bitsocodechallenge.databinding.ItemBookBinding
import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.utils.EventManager

class BookAdapter(private var books: List<Book>, private val onBookClick: (Book) -> Unit) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private var updateJob: Job? = null

    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) =
        holder.bind(books[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BookViewHolder(
        ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onBookClick
    )

    fun updateList(newItems: List<Book>) {
        updateJob = GlobalScope.launch(Dispatchers.IO) {
            val diff = DiffUtil.calculateDiff(BookDiffCallback(books, newItems))
            launch(Dispatchers.Main) {
                diff.dispatchUpdatesTo(this@BookAdapter)
                books = newItems
                EventManager.reportLoading(false)
            }
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        if (updateJob?.isActive == true) {
            updateJob?.cancel()
        }
        updateJob = null
    }

    class BookDiffCallback(private val oldItems: List<Book>, private val newItems: List<Book>) :
        DiffUtil.Callback() {
        override fun getOldListSize() = oldItems.size

        override fun getNewListSize() = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldItems[oldItemPosition].book == newItems[newItemPosition].book

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldItems[oldItemPosition].areContentsTheSame(newItems[newItemPosition])
    }

    class BookViewHolder(
        private val binding: ItemBookBinding,
        private val onBookClick: (Book) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Book) {
            binding.book = item
            binding.root.setOnClickListener { onBookClick(item) }
        }
    }
}