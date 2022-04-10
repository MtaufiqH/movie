package com.taufiq.movies.data.remote.web

import com.taufiq.movies.BuildConfig
import com.taufiq.movies.data.remote.model.MovieResponse
import com.taufiq.movies.data.remote.model.UpcomingResponse
import com.taufiq.movies.data.remote.web.url.EndPoint
import com.taufiq.movies.data.remote.web.url.ResponseMovie
import retrofit2.http.GET
import retrofit2.http.Query

interface WebClientService {

    @GET(EndPoint.Movies.GET_NOW_PLAYING)
    fun getNowPlaying(
        @Query("api_key") apiKey: String = BuildConfig.MOVIEDB_API_KEY
    ): ResponseMovie<List<MovieResponse>>

    @GET(EndPoint.Movies.GET_UP_COMING)
    fun getUpcoming(@Query("api_key") apiKey: String = BuildConfig.MOVIEDB_API_KEY
    ): ResponseMovie<List<UpcomingResponse>>

}
