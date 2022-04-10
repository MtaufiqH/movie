package com.taufiq.movies.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.taufiq.movies.data.local.dao.MovieDao
import com.taufiq.movies.data.local.entity.MovieEntity
import com.taufiq.movies.utils.MovieDb.DB_NAME

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        private var INSTANCE: MovieDatabase? = null

        fun getAppDatabase(appcontext: Context): MovieDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(
                        appcontext,
                        MovieDatabase::class.java,
                        DB_NAME
                    ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }
    }
}

