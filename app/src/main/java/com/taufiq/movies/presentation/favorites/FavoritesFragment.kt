package com.taufiq.movies.presentation.favorites

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.kennyc.view.MultiStateView
import com.taufiq.movies.R
import com.taufiq.movies.data.DataState
import com.taufiq.movies.databinding.FragmentFavoriteBinding
import com.taufiq.movies.domain.model.Movie
import com.taufiq.movies.presentation.movies.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FavoritesFragment : Fragment(R.layout.fragment_favorite) {

    private val binding by viewBinding<FragmentFavoriteBinding>()
    private val favoriteViewModel by sharedViewModel<MovieViewModel>()
    private lateinit var favoriteMovieAdapter: FavoriteMovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteViewModel.getAllFavoriteMovie()
        initViewModel()
    }

    private fun initViewModel() {
        favoriteViewModel.favMovies.observe(requireActivity()) {
            when (it) {
                is DataState.Empty -> binding.msvLayout.viewState = MultiStateView.ViewState.EMPTY
                is DataState.Error -> Log.e(
                    "favMovieObserver: error",
                    it.message.toString()
                )
                is DataState.Loading -> binding.msvLayout.viewState =
                    MultiStateView.ViewState.LOADING
                is DataState.Success -> {
                    binding.msvLayout.viewState = MultiStateView.ViewState.CONTENT
                    if (it.data?.isNotEmpty() == true) {
                        setupAdapter(it.data)
                    } else {
                        binding.msvLayout.viewState = MultiStateView.ViewState.EMPTY
                    }
                }
            }
        }
    }

    private fun setupAdapter(data: List<Movie>?) {
        with(binding) {
            rvFavorite.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                favoriteMovieAdapter = FavoriteMovieAdapter(requireContext()) { data ->
                    createDialog("OK", "NO", data)

                }
                favoriteMovieAdapter.setData(data)
                adapter = favoriteMovieAdapter
            }
        }
    }

    private fun createDialog(okMessage: String, noMessage: String, movie: Movie) {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle("Are you sure want to delete?")
            setMessage("This will delete your favorite movie in the list")
            setPositiveButton(okMessage) { _, _ ->
                favoriteViewModel.deleteFavoriteMovie(movie.id)
            }
            setNegativeButton(noMessage) { _, _ ->
                null
            }
        }

        builder.show()
    }
}