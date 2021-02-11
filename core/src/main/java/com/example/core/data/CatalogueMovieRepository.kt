package com.example.core.data

import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.local.entity.TvEntity
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.data.source.remote.response.TvResponse
import com.example.core.data.source.remote.service.ApiResponse
import com.example.core.domain.model.Movie
import com.example.core.domain.model.Tv
import com.example.core.domain.repository.ICatalogueMovieRepository
import com.example.core.utils.AppExecutors
import com.example.core.utils.DataMapper
import com.example.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CatalogueMovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    ICatalogueMovieRepository {

    override fun getAllMovie(): Flow<Resource<List<Movie>>> {
        return object :
            NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies().map {
                    DataMapper.mapMovieListEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovie()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieArrayList = DataMapper.mapMovieListResponseToEntities(data)
                localDataSource.insertMovies(movieArrayList)
            }

        }.asFlow()
    }

    override fun getAllTv(): Flow<Resource<List<Tv>>> {
        return object : NetworkBoundResource<List<Tv>, List<TvResponse>>() {
            override fun loadFromDB(): Flow<List<Tv>> {
                return localDataSource.getAllTvs().map {
                    DataMapper.mapTvListEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Tv>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<TvResponse>>> =
                remoteDataSource.getAllTv()

            override suspend fun saveCallResult(data: List<TvResponse>) {
                val tvArrayList = DataMapper.mapTvListResponseToEntities(data)
                localDataSource.insertTv(tvArrayList)
            }

        }.asFlow()
    }

    override fun getDetailMovie(id: Int): Flow<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, MovieResponse>() {
            override fun loadFromDB(): Flow<Movie> {
                return localDataSource.getDetailMovie(id).map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: Movie?): Boolean =
                data == null

            override suspend fun createCall(): Flow<ApiResponse<MovieResponse>> =
                remoteDataSource.getMovieDetail(id)

            override suspend fun saveCallResult(data: MovieResponse) {
                val movie = MovieEntity(
                    data.id,
                    data.title,
                    data.overview,
                    data.posterPath,
                    data.releaseDate,
                    data.voteAverage,
                    false
                )
                localDataSource.insertDetailMovie(movie)
            }

        }.asFlow()
    }

    override fun getDetailTv(id: Int): Flow<Resource<Tv>> {
        return object : NetworkBoundResource<Tv, TvResponse>() {
            override fun loadFromDB(): Flow<Tv> {
                return localDataSource.getDetailTv(id).map {
                    DataMapper.mapTvEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: Tv?): Boolean =
                data == null

            override suspend fun createCall(): Flow<ApiResponse<TvResponse>> =
                remoteDataSource.getTvDetail(id)

            override suspend fun saveCallResult(data: TvResponse) {
                val tv = TvEntity(
                    data.id,
                    data.name,
                    data.overview,
                    data.posterPath,
                    data.firstAirDate,
                    data.voteAverage,
                    false
                )
                localDataSource.insertDetailTv(tv)
            }

        }.asFlow()
    }

    override fun getMovieFavourites(): Flow<List<Movie>> {
        return localDataSource.getAllMovieFavourites().map {
            DataMapper.mapMovieListEntitiesToDomain(it)
        }
    }

    override fun setMovieFavourites(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapMovieDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }

    override fun getTvFavourites(): Flow<List<Tv>> {
        return localDataSource.getAllTvFavourites().map {
            DataMapper.mapTvListEntitiesToDomain(it)
        }
    }

    override fun setTvFavourites(tv: Tv, state: Boolean) {
        val tvEntity = DataMapper.mapTvDomainToEntity(tv)
        appExecutors.diskIO().execute { localDataSource.setFavoriteTv(tvEntity, state) }
    }
}