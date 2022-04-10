package com.taufiq.movies.presentation.movies

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import com.taufiq.movies.R
import com.taufiq.movies.data.DataState
import com.taufiq.movies.databinding.FragmentMoviesBinding
import com.taufiq.movies.domain.model.Movie
import com.taufiq.movies.domain.model.Upcoming
import com.taufiq.movies.presentation.upcoming.UpcomingAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private val binding by viewBinding<FragmentMoviesBinding>()
    private val viewmodel by sharedViewModel<MovieViewModel>()
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var upComingAdapter: UpcomingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        viewmodel.getMoviesNowPlaying()
        viewmodel.getAllUpcomingMovie()
    }

    private fun initViewModel() {
        with(binding) {
            viewmodel.movies.observe(requireActivity()) { movieState ->
                when (movieState) {
                    is DataState.Loading -> msvLayout.viewState = MultiStateView.ViewState.LOADING
                    is DataState.Success -> {
                        msvLayout.viewState = MultiStateView.ViewState.CONTENT
                        if (movieState.data?.isNotEmpty() == true) {
                            setupNowPlayingList(movieState.data)
                        }
                    }
                    is DataState.Empty -> {
                        msvLayout.viewState = MultiStateView.ViewState.EMPTY
                    }
                    is DataState.Error -> {
                        Log.i("initViewModel", movieState.message.toString())
                    }
                }
            }

            viewmodel.upcoming.observe(requireActivity()) {
                when (it) {
                    is DataState.Empty -> {
                        msvLayout2.viewState = MultiStateView.ViewState.EMPTY
                    }
                    is DataState.Error -> {
                        msvLayout2.viewState = MultiStateView.ViewState.EMPTY
                        Log.i("Upcoming Observer", "initViewModel: ${it.message}")
                    }
                    is DataState.Loading -> {
                        msvLayout2.viewState = MultiStateView.ViewState.LOADING
                    }
                    is DataState.Success -> {
                        msvLayout2.viewState = MultiStateView.ViewState.CONTENT
                        if (it.data?.isNotEmpty() == true) {
                            setupUpcomingList(it.data)
                        }
                    }
                }
            }

        }
    }

    private fun setupUpcomingList(data: List<Upcoming>?) {
        with(binding) {
            rvUpcoming.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            upComingAdapter = UpcomingAdapter(requireContext()).apply {
                setData(data)
            }
            rvUpcoming.adapter = upComingAdapter
        }
    }

    private fun setupNowPlayingList(data: List<Movie>?) {
        with(binding) {
            rvMovies.layoutManager = GridLayoutManager(requireContext(), 2)
            movieAdapter = MovieAdapter(requireContext()) { data ->
                viewmodel.setAsFavorite(data)
            }
            movieAdapter.setData(data)
            rvMovies.adapter = movieAdapter
        }
    }

}