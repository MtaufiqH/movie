package com.taufiq.movies

import android.app.Application
import com.taufiq.movies.di.appModule
import com.taufiq.movies.di.databaseModule
import com.taufiq.movies.di.networkModules
import com.taufiq.movies.di.rxModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovieApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MovieApplication)
            modules(
                networkModules,
                appModule,
                databaseModule,
                rxModule
            )
        }

    }


}