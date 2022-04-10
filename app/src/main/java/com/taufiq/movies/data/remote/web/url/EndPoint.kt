package com.taufiq.movies.data.remote.web.url

import com.taufiq.movies.data.remote.model.BaseResponse
import io.reactivex.Observable
import retrofit2.Response

typealias ResponseMovie<T> = Observable<Response<BaseResponse<T>>>

object EndPoint {
    const val BASE_URL = "https://api.themoviedb.org/3/"

    object Movies {
        const val GET_NOW_PLAYING = "movie/now_playing"
        const val GET_UP_COMING = "movie/upcoming"
    }
}

