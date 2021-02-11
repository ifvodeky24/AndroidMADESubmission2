package com.example.core.data.source.local.room

import androidx.room.*
import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.local.entity.TvEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogueMovieDao {

    @Query("SELECT * FROM movieentities")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tventities")
    fun getAllTvs(): Flow<List<TvEntity>>

    @Query("SELECT * FROM movieentities where favorite = 1")
    fun getAllFavoritesMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tventities where favorite = 1")
    fun getAllFavoritesTvs(): Flow<List<TvEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTv(tvs: List<TvEntity>)

    @Update
    fun updateMovies(movie: MovieEntity)

    @Update
    fun updateTvs(tv: TvEntity)

    @Transaction
    @Query("SELECT * FROM movieentities WHERE id = :id")
    fun getDetailMovie(id: Int): Flow<MovieEntity>

    @Transaction
    @Query("SELECT * FROM tventities WHERE id = :id")
    fun getDetailTv(id: Int): Flow<TvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailTv(tv: TvEntity)
}