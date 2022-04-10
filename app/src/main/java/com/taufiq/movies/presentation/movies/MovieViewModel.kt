package com.taufiq.movies.presentation.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taufiq.movies.data.DataState
import com.taufiq.movies.domain.MovieUseCase
import com.taufiq.movies.domain.model.Movie
import com.taufiq.movies.domain.model.Upcoming
import com.taufiq.movies.schedulers.SchedulerProviders
import com.taufiq.movies.utils.applySchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import retrofit2.HttpException

class MovieViewModel(
    private val movieUseCase: MovieUseCase,
    private val schedulerProviders: SchedulerProviders,
    private val disposable: CompositeDisposable
) : ViewModel() {

    private val _movies = MutableLiveData<DataState<List<Movie>>>()
    val movies: LiveData<DataState<List<Movie>>> = _movies

    private val _upcoming = MutableLiveData<DataState<List<Upcoming>>>()
    val upcoming: LiveData<DataState<List<Upcoming>>> = _upcoming

    private val _favMovies = MutableLiveData<DataState<List<Movie>>>()
    val favMovies: LiveData<DataState<List<Movie>>> = _favMovies

    private val _state = MutableLiveData<DataState<String>>()
    val state: LiveData<DataState<String>> = _state

    fun getMoviesNowPlaying() {
        _movies.value = DataState.Loading()
        movieUseCase.getNowPlaying()
            .applySchedulers(schedulerProviders)
            .subscribeBy(
                onNext = { movies ->
                    _movies.value = DataState.Success(movies)
                },
                onError = {
                    if (it is HttpException) {
                        val errorBody = it.response()?.errorBody().toString()
                        if (errorBody.isNotEmpty()) {
                            _movies.value = DataState.Error(errorBody)
                        } else {
                            _movies.value = DataState.Empty()
                        }
                    } else {
                        val error = it.localizedMessage
                        _movies.value = DataState.Error(error)
                    }
                }).addTo(disposable)
    }

    fun getAllUpcomingMovie() {
        _upcoming.value = DataState.Loading()
        movieUseCase.getUpcoming().applySchedulers(schedulerProviders).subscribeBy(
            onNext = {
                if (it.isEmpty()) {
                    _upcoming.value = DataState.Empty()
                } else {
                    _upcoming.value = DataState.Success(it)
                }
            },
            onError = {
                if (it is HttpException) {
                    val error = it.response()?.errorBody().toString()
                    if (error.isNotEmpty()) {
                        _upcoming.value = DataState.Error(error)
                    } else {
                        _upcoming.value = DataState.Empty()
                    }
                } else {
                    val error = it.localizedMessage
                    _upcoming.value = DataState.Error(error)
                }
            }).addTo(disposable)
    }

    fun getAllFavoriteMovie() {
        _favMovies.value = DataState.Loading()
        movieUseCase.getAllFavoriteMovies().applySchedulers(schedulerProviders).subscribeBy(
            onNext = {
                if (it.isNotEmpty()) {
                    _favMovies.value = DataState.Success(it)
                } else {
                    _favMovies.value = DataState.Empty()
                }
            },
            onError = {
                val message = it.message.toString()
                _favMovies.value = DataState.Error(message)
            }
        ).addTo(disposable)

    }

    fun setAsFavorite(movie: Movie) {
        _state.value = DataState.Loading()
        movieUseCase.setAsFavoriteMovie(movie).subscribeOn(schedulerProviders.io())
            .observeOn(schedulerProviders.mainThread())
            .subscribeBy(
                onError = { _state.value = DataState.Error("Error add as favorite") },
                onComplete = {
                    _state.value = DataState.Success("Set as Favorited")
                }).addTo(disposable)
    }

    fun deleteFavoriteMovie(id: Int) {
        movieUseCase.deleteFavoriteMovie(id).subscribeOn(schedulerProviders.io())
            .observeOn(schedulerProviders.mainThread())
            .subscribeBy(onError = {
                Log.i("deleteFavoriteMovie", "error ${it.message}")
            }, onComplete = {
                Log.i("deleteFavoriteMovie", "Succes deleting")
            }).addTo(disposable)
    }


    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}