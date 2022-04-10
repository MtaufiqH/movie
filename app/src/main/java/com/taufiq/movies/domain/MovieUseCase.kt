package com.taufiq.movies.domain

import com.taufiq.movies.domain.model.Movie
import com.taufiq.movies.domain.model.Upcoming
import io.reactivex.Completable
import io.reactivex.Observable

interface MovieUseCase {
    fun getNowPlaying(): Observable<List<Movie>>
    fun getUpcoming(): Observable<List<Upcoming>>
    fun getAllFavoriteMovies(): Observable<List<Movie>>
    fun setAsFavoriteMovie(favMovie: Movie): Completable
    fun deleteFavoriteMovie(movieId: Int): Completable
}