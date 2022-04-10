package com.taufiq.movies.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.taufiq.movies.data.MovieDataStore
import com.taufiq.movies.data.MovieRepository
import com.taufiq.movies.data.local.MovieDatabase
import com.taufiq.movies.data.remote.web.WebClientService
import com.taufiq.movies.data.remote.web.url.EndPoint
import com.taufiq.movies.domain.MovieInteractor
import com.taufiq.movies.domain.MovieUseCase
import com.taufiq.movies.presentation.movies.MovieViewModel
import com.taufiq.movies.schedulers.SchedulerProviders
import com.taufiq.movies.schedulers.SchedulerProvidersImpl
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModules = module {
    single {
        GsonBuilder()
            .setPrettyPrinting()
            .setLenient()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(EndPoint.BASE_URL)
            .client(get())
            .build()
        retrofit.create(WebClientService::class.java)
    }
}

val appModule = module {
    factory<SchedulerProviders> { SchedulerProvidersImpl() }
    single<MovieRepository> { MovieDataStore(get(), get(), get()) }
    single<MovieUseCase> { MovieInteractor(get()) }

    viewModel {
        MovieViewModel(get(), get(), get())
    }

}

val databaseModule = module {
    single { get<MovieDatabase>().movieDao() }
    single {
        MovieDatabase.getAppDatabase(androidContext())
    }
}

val rxModule = module {
    single {
        CompositeDisposable()
    }
}
