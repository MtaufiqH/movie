package com.taufiq.movies.utils

import com.taufiq.movies.data.local.entity.MovieEntity
import com.taufiq.movies.data.remote.model.MovieResponse
import com.taufiq.movies.data.remote.model.UpcomingResponse
import com.taufiq.movies.domain.model.Movie
import com.taufiq.movies.domain.model.Upcoming

object Mapper {
    fun MovieResponse?.movieResponseToMovie(): Movie =
        Movie(
            this?.adult ?: false,
            this?.backdropPath.orEmpty(),
            this?.genreIds.orEmpty(),
            this?.id.orNol(),
            this?.originalLanguage.orEmpty(),
            this?.originalTitle.orEmpty(),
            this?.overview.orEmpty(),
            this?.popularity.orNol(),
            this?.posterPath.orEmpty(),
            this?.releaseDate.orEmpty(),
            this?.title.orEmpty(),
            this?.video ?: false,
            this?.voteAverage.orNol(),
            this?.voteCount.orNol(),
            this?.isFavorite ?: false
        )

    fun UpcomingResponse?.upcomingResponseToUpcoming(): Upcoming =
        Upcoming(
            this?.adult ?: false,
            this?.backdropPath.orEmpty(),
            this?.genreIds.orEmpty(),
            this?.id.orNol(),
            this?.originalLanguage.orEmpty(),
            this?.originalTitle.orEmpty(),
            this?.overview.orEmpty(),
            this?.popularity.orNol(),
            this?.posterPath.orEmpty(),
            this?.releaseDate.orEmpty(),
            this?.title.orEmpty(),
            this?.video ?: false,
            this?.voteAverage.orNol(),
            this?.voteCount.orNol(),
        )


    fun MovieEntity?.movieEntityToMovie(): Movie =
        Movie(
            posterPath = this?.imagePath.orEmpty(),
            id = this?.id.orNol(),
            originalTitle = this?.movieTitle.orEmpty(),
            overview = this?.movieOverview.orEmpty(),
            isFavorite = this?.isFavorite ?: false
        )

    fun Movie?.movieToMovieEntity(): MovieEntity =
        MovieEntity(
            this?.id.orNol(),
            this?.posterPath.orEmpty(),
            this?.title.orEmpty(),
            this?.overview.orEmpty(),
            this?.isFavorite ?: false
        )
}