package com.example.core.data.source.remote.service

import com.example.core.BuildConfig.API_KEY
import com.example.core.data.source.remote.response.MovieListResponse
import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.data.source.remote.response.TvListResponse
import com.example.core.data.source.remote.response.TvResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String? = API_KEY,
        @Query("language") language: String? = "en-US",
        @Query("page") page: Int
    ): MovieListResponse

    @GET("tv/popular")
    suspend fun getPopularTv(
        @Query("api_key") apiKey: String? = API_KEY,
        @Query("language") language: String? = "en-US",
        @Query("page") page: Int
    ): TvListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String? = API_KEY,
        @Query("language") language: String? = "en-US"
    ): MovieResponse

    @GET("tv/{tv_id}")
    suspend fun getTvDetail(
        @Path("tv_id") id: Int,
        @Query("api_key") apiKey: String? = API_KEY,
        @Query("language") language: String? = "en-US"
    ): TvResponse
}