package me.alfredobejarano.bitsocodechallenge.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import me.alfredobejarano.bitsocodechallenge.databinding.FragmentBookTickerBinding
import me.alfredobejarano.bitsocodechallenge.utils.viewBinding

/**
 * TickerFragment
 *
 * @author (c) AlfredoBejarano - alfredo.corona@rappi.com
 */
class TickerFragment : Fragment() {
    private val args: TickerFragmentArgs by navArgs()
    private val binding: FragmentBookTickerBinding by viewBinding(FragmentBookTickerBinding::inflate)

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?) =
        binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.book = args.Book
        binding.backControlIcon.setOnClickListener { findNavController().navigateUp() }
    }
}