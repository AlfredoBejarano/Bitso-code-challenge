package me.alfredobejarano.bitsocodechallenge.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE
import dagger.hilt.android.AndroidEntryPoint
import me.alfredobejarano.bitsocodechallenge.R
import me.alfredobejarano.bitsocodechallenge.R.string.generic_error_message
import me.alfredobejarano.bitsocodechallenge.databinding.FragmentBookListBinding
import me.alfredobejarano.bitsocodechallenge.databinding.ItemBookBinding
import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.utils.observeResult
import me.alfredobejarano.bitsocodechallenge.utils.setLoadingState
import me.alfredobejarano.bitsocodechallenge.utils.showException
import me.alfredobejarano.bitsocodechallenge.utils.viewBinding
import me.alfredobejarano.bitsocodechallenge.view.adapter.BookAdapter
import me.alfredobejarano.bitsocodechallenge.viewmodel.BookListViewModel

@AndroidEntryPoint
class BookListFragment : Fragment() {
    private var snackBar: Snackbar? = null
    private val viewModel: BookListViewModel by viewModels()
    private val binding: FragmentBookListBinding by viewBinding(FragmentBookListBinding::inflate)

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?) =
        binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        createErrorSnackBar()
        observeResult(
            liveData = viewModel.booksLiveData,
            onSuccess = ::updateList,
            onError = snackBar::showException
        )
        getBooks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        snackBar = null
    }

    /**
     * Add callbacks and listeners for the UI widgets of this fragment.
     */
    private fun setupViews() = binding.run {
        bookListView.layoutManager = LinearLayoutManager(requireContext())
        swipeRefreshRoot.setOnRefreshListener(::getBooks)
    }

    /**
     * Creates the [Snackbar] object responsible of showing errors.
     */
    private fun createErrorSnackBar() {
        snackBar = Snackbar.make(binding.root, generic_error_message, LENGTH_INDEFINITE)
        snackBar?.setAction(R.string.retry) { viewModel.getBooks() }
    }

    /**
     * Retrieves the list of available books from the [viewModel][BookListViewModel].
     */
    private fun getBooks() {
        binding.root.setLoadingState(true)
        viewModel.getBooks()
    }

    /**
     * After receiving a [List of Books][Book], proceeds to either, update the current UI widget
     * that displays the books or to fill said widget.
     *
     * @param books - The collection of [Book] objects to be displayed.
     */
    private fun updateList(books: List<Book>) {
        (binding.bookListView.adapter as? BookAdapter)?.updateList(books) ?: createAdapter(books)
        binding.root.setLoadingState(false)
    }

    /**
     * Creates an [adapter class][BookAdapter] that will handle how a [list of books][Book] will
     * be displayed within a recycler view.
     * @param books - The collection of [Book] objects to be displayed.
     */
    private fun createAdapter(books: List<Book>) {
        binding.bookListView.adapter = BookAdapter(books, ::showBookTicker) { inflater, parent ->
            ItemBookBinding.inflate(inflater, parent, false)
        }
    }

    /**
     * Displays more details when clicking a book.
     * @param book - The [book] object to display.
     */
    private fun showBookTicker(book: Book) =
        findNavController().navigate(BookListFragmentDirections.showBookTicker(book))
}