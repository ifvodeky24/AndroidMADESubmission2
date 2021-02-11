package com.example.core.utils

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.local.entity.TvEntity
import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.data.source.remote.response.TvResponse
import com.example.core.domain.model.Movie
import com.example.core.domain.model.Tv

object DataMapper {
    fun mapMovieListResponseToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                favorite = false,
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapTvListResponseToEntities(input: List<TvResponse>): List<TvEntity> {
        val tvList = ArrayList<TvEntity>()
        input.map {
            val tv = TvEntity(
                id = it.id,
                name = it.name,
                overview = it.overview,
                posterPath = it.posterPath,
                firstAirDate = it.firstAirDate,
                voteAverage = it.voteAverage,
                favorite = false,
            )
            tvList.add(tv)
        }
        return tvList
    }

    fun mapMovieListEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                favorite = it.favorite
            )
        }

    fun mapMovieEntitiesToDomain(input: MovieEntity) = Movie(
        id = input.id,
        title = input.title,
        overview = input.overview,
        posterPath = input.posterPath,
        releaseDate = input.releaseDate,
        voteAverage = input.voteAverage,
        favorite = input.favorite
    )

    fun mapMovieDomainToEntity(input: Movie) = MovieEntity(
        id = input.id,
        title = input.title,
        overview = input.overview,
        posterPath = input.posterPath,
        releaseDate = input.releaseDate,
        voteAverage = input.voteAverage,
        favorite = input.favorite
    )

    fun mapTvListEntitiesToDomain(input: List<TvEntity>): List<Tv> =
        input.map {
            Tv(
                id = it.id,
                name = it.name,
                overview = it.overview,
                posterPath = it.posterPath,
                firstAirDate = it.firstAirDate,
                voteAverage = it.voteAverage,
                favorite = it.favorite,
            )
        }

    fun mapTvEntitiesToDomain(input: TvEntity) = Tv(
        id = input.id,
        name = input.name,
        overview = input.overview,
        posterPath = input.posterPath,
        firstAirDate = input.firstAirDate,
        voteAverage = input.voteAverage,
        favorite = input.favorite,
    )

    fun mapTvDomainToEntity(input: Tv) = TvEntity(
        id = input.id,
        name = input.name,
        overview = input.overview,
        posterPath = input.posterPath,
        firstAirDate = input.firstAirDate,
        voteAverage = input.voteAverage,
        favorite = input.favorite,
    )
}