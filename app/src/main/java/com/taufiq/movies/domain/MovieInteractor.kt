package com.taufiq.movies.domain

import com.taufiq.movies.data.MovieRepository
import com.taufiq.movies.domain.model.Movie
import com.taufiq.movies.domain.model.Upcoming
import com.taufiq.movies.utils.Mapper
import com.taufiq.movies.utils.Mapper.movieEntityToMovie
import com.taufiq.movies.utils.Mapper.movieResponseToMovie
import com.taufiq.movies.utils.Mapper.movieToMovieEntity
import com.taufiq.movies.utils.Mapper.upcomingResponseToUpcoming
import io.reactivex.Completable
import io.reactivex.Observable

class MovieInteractor(private val repository: MovieRepository) : MovieUseCase {
    override fun getNowPlaying(): Observable<List<Movie>> {
        return repository.getNowPlaying().map {
            it.map { response ->
                response.movieResponseToMovie()
            }
        }
    }

    override fun getUpcoming(): Observable<List<Upcoming>> {
        return repository.getUpcoming().map {
            it.map { upcomingResponse ->
                upcomingResponse.upcomingResponseToUpcoming()
            }
        }
    }

    override fun getAllFavoriteMovies(): Observable<List<Movie>> {
        return repository.getAllFavoriteMovies().map {
            it.map { entity ->
                entity.movieEntityToMovie()
            }
        }
    }

    override fun setAsFavoriteMovie(favMovie: Movie): Completable {
        return repository.setAsFavorite(favMovie.movieToMovieEntity())
    }


    override fun deleteFavoriteMovie(movieId: Int): Completable {
        return repository.deleteFavorite(movieId)
    }
}