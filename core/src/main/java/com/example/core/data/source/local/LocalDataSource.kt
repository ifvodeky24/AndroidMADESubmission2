package com.example.core.data.source.local

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.local.entity.TvEntity
import com.example.core.data.source.local.room.CatalogueMovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val catalogueMovieDao: CatalogueMovieDao) {

    fun getAllMovies(): Flow<List<MovieEntity>> = catalogueMovieDao.getAllMovies()

    fun getAllTvs(): Flow<List<TvEntity>> = catalogueMovieDao.getAllTvs()

    fun getAllMovieFavourites(): Flow<List<MovieEntity>> =
        catalogueMovieDao.getAllFavoritesMovies()

    fun getAllTvFavourites(): Flow<List<TvEntity>> =
        catalogueMovieDao.getAllFavoritesTvs()

    suspend fun insertMovies(movies: List<MovieEntity>) {
        catalogueMovieDao.insertMovie(movies)
    }

    suspend fun insertTv(tvs: List<TvEntity>) {
        catalogueMovieDao.insertTv(tvs)
    }

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.favorite = newState
        catalogueMovieDao.updateMovies(movie)
    }

    fun setFavoriteTv(tv: TvEntity, newState: Boolean) {
        tv.favorite = newState
        catalogueMovieDao.updateTvs(tv)
    }

    fun getDetailMovie(id: Int): Flow<MovieEntity> = catalogueMovieDao.getDetailMovie(id)

    fun getDetailTv(id: Int): Flow<TvEntity> = catalogueMovieDao.getDetailTv(id)

    suspend fun insertDetailMovie(movie: MovieEntity) {
        catalogueMovieDao.insertDetailMovie(movie)
    }

    suspend fun insertDetailTv(tv: TvEntity) {
        catalogueMovieDao.insertDetailTv(tv)
    }
}