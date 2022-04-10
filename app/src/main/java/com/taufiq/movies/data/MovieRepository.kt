package com.taufiq.movies.data

import com.taufiq.movies.data.local.entity.MovieEntity
import com.taufiq.movies.data.remote.model.MovieResponse
import com.taufiq.movies.data.remote.model.UpcomingResponse
import io.reactivex.Completable
import io.reactivex.Observable

interface MovieRepository {
    fun getNowPlaying(): Observable<List<MovieResponse>>
    fun getUpcoming(): Observable<List<UpcomingResponse>>
    fun getAllFavoriteMovies(): Observable<List<MovieEntity>>
    fun setAsFavorite(movie: MovieEntity) : Completable
    fun deleteFavorite(id: Int): Completable
}