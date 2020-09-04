package me.alfredobejarano.bitsocodechallenge.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.alfredobejarano.bitsocodechallenge.databinding.FragmentBookListBinding
import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.utils.EventManager
import me.alfredobejarano.bitsocodechallenge.utils.viewBinding
import me.alfredobejarano.bitsocodechallenge.view.adapter.BookAdapter
import me.alfredobejarano.bitsocodechallenge.viewmodel.BookListViewModel

@AndroidEntryPoint
class BookListFragment : Fragment() {
    private val viewModel: BookListViewModel by viewModels()
    private val binding: FragmentBookListBinding by viewBinding(FragmentBookListBinding::inflate)

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?) =
        binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bookListView.layoutManager = LinearLayoutManager(requireContext())
        binding.swipeRefreshRoot.setOnRefreshListener { viewModel.getBooks() }

        EventManager.loadingLiveData.observe(viewLifecycleOwner, Observer {
            binding.swipeRefreshRoot.isRefreshing = it
        })

        viewModel.booksLiveData.observe(viewLifecycleOwner, Observer(::updateList))
        viewModel.getBooks()
    }

    private fun updateList(books: List<Book>) = binding.bookListView.run {
        (adapter as? BookAdapter)?.updateList(books) ?: run {
            adapter = BookAdapter(books) { book ->
                Log.d("BOOK", "TODO - Take me to the next screen! ${book.book}")
            }
            EventManager.reportLoading(false)
        }
    }
}