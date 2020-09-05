package me.alfredobejarano.bitsocodechallenge.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE
import dagger.hilt.android.AndroidEntryPoint
import me.alfredobejarano.bitsocodechallenge.R
import me.alfredobejarano.bitsocodechallenge.R.string.generic_error_message
import me.alfredobejarano.bitsocodechallenge.databinding.FragmentBookListBinding
import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.utils.EventManager
import me.alfredobejarano.bitsocodechallenge.utils.observe
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
        createSnackBar()
        observeEventManager()
        observeViewModel()
    }

    private fun setupViews() = binding.run {
        bookListView.layoutManager = LinearLayoutManager(requireContext())
        swipeRefreshRoot.setOnRefreshListener { viewModel.getBooks() }
    }

    private fun observeViewModel() = viewModel.run {
        observe(booksLiveData, ::updateList)
        getBooks()
    }

    private fun updateList(books: List<Book>) =
        (binding.bookListView.adapter as? BookAdapter)?.updateList(books) ?: createAdapter(books)

    private fun createAdapter(books: List<Book>) {
        binding.bookListView.adapter = BookAdapter(books, ::showBookTicker)
        EventManager.reportLoading(false)
    }

    private fun showBookTicker(book: Book) =
        findNavController().navigate(BookListFragmentDirections.showBookTicker(book))

    private fun createSnackBar() {
        snackBar = Snackbar.make(binding.root, generic_error_message, LENGTH_INDEFINITE)
        snackBar?.setAction(R.string.retry) { viewModel.getBooks() }
    }

    private fun observeEventManager() = EventManager.run {
        loadingLiveData.observe(viewLifecycleOwner, Observer {
            binding.swipeRefreshRoot.isRefreshing = it
        })
        errorLiveData.observe(viewLifecycleOwner, Observer {
            if (snackBar?.isShown == false) {
                snackBar?.show()
            }
        })
    }
}