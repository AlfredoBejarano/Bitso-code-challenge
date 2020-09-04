package me.alfredobejarano.bitsocodechallenge.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import me.alfredobejarano.bitsocodechallenge.viewmodel.BookListViewModel

@AndroidEntryPoint
class BookListFragment : Fragment() {
    private val viewModel: BookListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?) =
        View(parent?.context)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getBooks().observe(viewLifecycleOwner, Observer {
            it.forEach { book ->
                Log.d("Book", book.toString())
            }
        })
    }
}