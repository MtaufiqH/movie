package com.taufiq.movies.data

import com.taufiq.movies.data.local.dao.MovieDao
import com.taufiq.movies.data.local.entity.MovieEntity
import com.taufiq.movies.data.remote.model.MovieResponse
import com.taufiq.movies.data.remote.model.UpcomingResponse
import com.taufiq.movies.data.remote.web.WebClientService
import com.taufiq.movies.schedulers.SchedulerProviders
import com.taufiq.movies.utils.applySchedulers
import com.taufiq.movies.utils.mapObservable
import io.reactivex.Completable
import io.reactivex.Observable

class MovieDataStore(
    private val webService: WebClientService,
    private val localData: MovieDao,
    private val schedulers: SchedulerProviders,
) : MovieRepository {

    override fun getNowPlaying(): Observable<List<MovieResponse>> {
        val response = getAllFavoriteMovies().map {
            it.map {
                it.id
            }
        }
       val disposable =  response.flatMap { data ->
            val webService = webService.getNowPlaying().applySchedulers(schedulers).mapObservable { it }
            webService.map {
                it.forEach { singleData ->
                    if(data.contains(singleData.id ?: -1)){
                        singleData.isFavorite = true
                    }
                }
                it
            }
        }

        return disposable.applySchedulers(schedulers)
    }

    override fun getUpcoming(): Observable<List<UpcomingResponse>> {
        return webService.getUpcoming().applySchedulers(schedulers).mapObservable { it }
    }

    override fun getAllFavoriteMovies(): Observable<List<MovieEntity>> {
        return localData.getAllFavoriteMovie().applySchedulers(schedulers)
    }

    override fun setAsFavorite(movie: MovieEntity): Completable {
        return localData.setAsFavoriteMovie(movie)
    }

    override fun deleteFavorite(id: Int): Completable {
        return localData.deleteFavorite(id)
    }
}