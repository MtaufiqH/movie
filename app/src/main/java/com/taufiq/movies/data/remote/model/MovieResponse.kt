package com.taufiq.movies.data.remote.model


import com.google.gson.annotations.SerializedName
import com.taufiq.movies.domain.model.Movie
import com.taufiq.movies.utils.orNol

data class MovieResponse(
    @SerializedName("adult")
    val adult: Boolean? = null,
    @SerializedName("backdrop_path")
    val backdropPath: String? = "",
    @SerializedName("genre_ids")
    val genreIds: List<Int>? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("original_language")
    val originalLanguage: String? = "",
    @SerializedName("original_title")
    val originalTitle: String? = "",
    @SerializedName("overview")
    val overview: String? = "",
    @SerializedName("popularity")
    val popularity: Double? = null,
    @SerializedName("poster_path")
    val posterPath: String? = "",
    @SerializedName("release_date")
    val releaseDate: String? = "",
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("video")
    val video: Boolean? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null,
    var isFavorite: Boolean = false
)