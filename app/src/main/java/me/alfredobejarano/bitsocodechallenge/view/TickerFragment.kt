package me.alfredobejarano.bitsocodechallenge.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE
import dagger.hilt.android.AndroidEntryPoint
import me.alfredobejarano.bitsocodechallenge.R
import me.alfredobejarano.bitsocodechallenge.R.string.generic_error_message
import me.alfredobejarano.bitsocodechallenge.databinding.FragmentBookTickerBinding
import me.alfredobejarano.bitsocodechallenge.databinding.ItemBookQuickDetailBinding
import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.utils.EventManager
import me.alfredobejarano.bitsocodechallenge.utils.observe
import me.alfredobejarano.bitsocodechallenge.utils.showSafely
import me.alfredobejarano.bitsocodechallenge.utils.viewBinding
import me.alfredobejarano.bitsocodechallenge.view.adapter.BookAdapter
import me.alfredobejarano.bitsocodechallenge.viewmodel.TickerViewModel

/**
 * TickerFragment
 */
@AndroidEntryPoint
class TickerFragment : Fragment() {
    private var snackBar: Snackbar? = null
    private val args: TickerFragmentArgs by navArgs()
    private val viewModel: TickerViewModel by viewModels()
    private val binding: FragmentBookTickerBinding by viewBinding(FragmentBookTickerBinding::inflate)

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?) =
        binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createSnackBar()
        observeEvents()
        setupViews()
        viewModel.setup(args.Book.book)
        observe(viewModel.bookLiveData, ::updateBook)
        observe(viewModel.booksLiveData) { it.requireNoNulls().run(::updateAdapter) }
    }

    private fun setupViews() = binding.run {
        booksQuickDetailsList.layoutManager = LinearLayoutManager(root.context, HORIZONTAL, false)
        lifecycleOwner = viewLifecycleOwner
        book = args.Book
        backControlIcon.setOnClickListener { findNavController().navigateUp() }
    }

    private fun updateBook(book: Book?) {
        book?.run(binding::setBook)
        EventManager.reportLoading(false)
    }

    private fun createSnackBar() {
        snackBar = Snackbar.make(binding.root, generic_error_message, LENGTH_INDEFINITE)
        snackBar?.setAction(R.string.retry) { viewModel.getBook() }
    }

    private fun observeEvents() = EventManager.run {
        observe(loadingLiveData) {
            binding.updateProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
        observe(errorLiveData) { snackBar.showSafely() }
    }

    private fun updateAdapter(books: List<Book>) =
        (binding.booksQuickDetailsList.adapter as? BookAdapter)?.updateList(books)
            ?: createAdapter(books)

    private fun createAdapter(books: List<Book>) {
        binding.booksQuickDetailsList.adapter =
            BookAdapter(books, ::showBook) { inflater, parent ->
                ItemBookQuickDetailBinding.inflate(inflater, parent, false)
            }
        EventManager.reportLoading(false)
    }

    private fun showBook(book: Book) {
        viewModel.setup(book.book)
        binding.book = book
        binding.executePendingBindings()
    }
}