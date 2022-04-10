package com.taufiq.movies.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.taufiq.movies.utils.MovieDb.IMAGE_PATH
import com.taufiq.movies.utils.MovieDb.IS_FAVORITE
import com.taufiq.movies.utils.MovieDb.MOVIE_ID
import com.taufiq.movies.utils.MovieDb.MOVIE_OVERVIEW
import com.taufiq.movies.utils.MovieDb.MOVIE_TITLE
import com.taufiq.movies.utils.MovieDb.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = MOVIE_ID)
    val id: Int? = null,
    @ColumnInfo(name = IMAGE_PATH)
    val imagePath: String? = null,
    @ColumnInfo(name = MOVIE_TITLE)
    val movieTitle: String? = null,
    @ColumnInfo(name = MOVIE_OVERVIEW)
    val movieOverview: String? = null,
    @ColumnInfo(name = IS_FAVORITE)
    var isFavorite: Boolean = false
)
