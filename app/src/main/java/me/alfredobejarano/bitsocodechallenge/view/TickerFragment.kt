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
import me.alfredobejarano.bitsocodechallenge.utils.observeResult
import me.alfredobejarano.bitsocodechallenge.utils.setLoadingState
import me.alfredobejarano.bitsocodechallenge.utils.showException
import me.alfredobejarano.bitsocodechallenge.utils.viewBinding
import me.alfredobejarano.bitsocodechallenge.view.adapter.BookAdapter
import me.alfredobejarano.bitsocodechallenge.viewmodel.TickerViewModel

/**
 * TickerFragment
 */
@AndroidEntryPoint
class TickerFragment : Fragment() {
    private var bookListErrorSnackBar: Snackbar? = null
    private var bookTickerErrorSnackBar: Snackbar? = null

    private val args: TickerFragmentArgs by navArgs()
    private val viewModel: TickerViewModel by viewModels()
    private val binding: FragmentBookTickerBinding by viewBinding(FragmentBookTickerBinding::inflate)

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?) =
        binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createSnackBars()
        setupViews()
        setupViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bookListErrorSnackBar = null
        bookTickerErrorSnackBar = null
    }

    /**
     * Creates the [Snackbar] objects that display errors.
     */
    private fun createSnackBars() {
        bookTickerErrorSnackBar = createSnackBar { viewModel.getBooks() }
        bookTickerErrorSnackBar = createSnackBar { viewModel.getBookTicker() }
    }

    /**
     * Configures the ViewModel with the navArg arguments and observes LiveData
     * objects from the ViewModel.
     */
    private fun setupViewModel() {
        viewModel.setup(args.Book)
        observeResult(
            liveData = viewModel.tickerLiveData,
            onSuccess = ::updateBook,
            onError = bookTickerErrorSnackBar::showException
        )
        observeResult(
            liveData = viewModel.booksLiveData,
            onSuccess = ::updateAdapter,
            onLoading = ::setLoadingBooksState,
            onError = bookListErrorSnackBar::showException
        )
    }

    /**
     * Sets the loading state for the list showing books.
     */
    private fun setLoadingBooksState(isLoading: Boolean) = binding.run {
        booksQuickDetailsList.setLoadingState(isLoading)
        bookListLoadingLottieAnimationView.setLoadingState(isLoading)
    }

    /**
     * Draws the book details into this view.
     */
    private fun showBook(book: Book) {
        updateBook(book)
    }

    /**
     * Add callbacks and listeners to the UI components of this class.
     */
    private fun setupViews() = binding.run {
        booksQuickDetailsList.layoutManager = LinearLayoutManager(root.context, HORIZONTAL, false)
        lifecycleOwner = viewLifecycleOwner
        book = args.Book
        backControlIcon.setOnClickListener { findNavController().navigateUp() }
    }

    /**
     * Updates the [Book] data in the UI.
     */
    private fun updateBook(book: Book) = binding.run {
        this.book = book
        executePendingBindings()
    }

    /**
     * Creates an [Snackbar] object with the given lambda as it's retry action.
     * @param action - The function to be executed when the "Retry" button gets clicked.
     */
    private fun createSnackBar(action: (View) -> Unit) =
        Snackbar.make(binding.root, generic_error_message, LENGTH_INDEFINITE)
            .setAction(R.string.retry, action)

    /**
     * Updates the Adapter for the RecyclerView displaying the [List] of books.
     * @param books - The list of [Book] objects to be displayed.
     */
    private fun updateAdapter(books: List<Book>) =
        (binding.booksQuickDetailsList.adapter as? BookAdapter)?.updateList(books)
            ?: createAdapter(books)

    /**
     * Creates an adapter displaying the details of related books.
     * @param books - The list of [Book] objects to be displayed.
     */
    private fun createAdapter(books: List<Book>) {
        binding.booksQuickDetailsList.adapter =
            BookAdapter(books, ::showBook) { inflater, parent ->
                ItemBookQuickDetailBinding.inflate(inflater, parent, false)
            }
    }
}