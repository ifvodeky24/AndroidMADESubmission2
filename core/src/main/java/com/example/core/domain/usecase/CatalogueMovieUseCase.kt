package com.example.core.domain.usecase

import com.example.core.domain.model.Movie
import com.example.core.domain.model.Tv
import com.example.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface CatalogueMovieUseCase {
    fun getAllMovie(): Flow<Resource<List<Movie>>>

    fun getAllTv(): Flow<Resource<List<Tv>>>

    fun getDetailMovie(id: Int): Flow<Resource<Movie>>

    fun getDetailTv(id: Int): Flow<Resource<Tv>>

    fun getMovieFavourites(): Flow<List<Movie>>

    fun setMovieFavourites(movie: Movie, state: Boolean)

    fun getTvFavourites(): Flow<List<Tv>>

    fun setTvFavourites(tv: Tv, state: Boolean)
}