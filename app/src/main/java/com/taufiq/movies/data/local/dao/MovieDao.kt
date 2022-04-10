package com.taufiq.movies.data.local.dao

import androidx.room.*
import com.taufiq.movies.data.local.entity.MovieEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface MovieDao {

    @Query("SELECT * FROM favorite_movie_table")
    fun getAllFavoriteMovie(): Observable<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setAsFavoriteMovie(movie: MovieEntity): Completable

    @Query("DELETE FROM favorite_movie_table WHERE id = :id")
    fun deleteFavorite(id: Int): Completable
}