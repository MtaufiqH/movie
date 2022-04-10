package com.taufiq.movies.utils

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.taufiq.movies.data.remote.web.url.ResponseMovie
import com.taufiq.movies.schedulers.SchedulerProviders
import io.reactivex.Observable

fun <T> Observable<T>.applySchedulers(providers: SchedulerProviders): Observable<T> {
    return subscribeOn(providers.io()).observeOn(providers.mainThread())
}

fun <T : Any, U : Any> ResponseMovie<T>.mapObservable(
    mapper: (T) -> U,
): Observable<U> {
    return flatMap { response ->
        if (response.isSuccessful) {
            val body = response.body()
            val data = body?.results
            if (data != null) {
                val dataMapper = mapper.invoke(data)
                Observable.just(dataMapper)
            } else {
                val exception = Throwable("Response data is null")
                Observable.error(exception)
            }
        } else {
            val bodyError = response.errorBody()?.string()
            val gson = GsonBuilder()
                .setPrettyPrinting()
                .setLenient()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

            val typeToken = object : TypeToken<ResponseMovie<Any>>() {}.type
            val bodyErrorData = gson.fromJson<ResponseMovie<Any>>(bodyError, typeToken)
            println("base response data -> $bodyErrorData")
            val messageHttp = response.message()
            val message = "$messageHttp, message $bodyErrorData"
            val exception = Throwable(message)
            Observable.error(exception)
        }

    }

}