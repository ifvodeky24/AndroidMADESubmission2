package com.example.core.domain.usecase

import com.example.core.domain.model.Movie
import com.example.core.domain.model.Tv
import com.example.core.domain.repository.ICatalogueMovieRepository
import kotlinx.coroutines.flow.Flow

class CatalogueMovieInteractor(private val catalogueMovieRepository: ICatalogueMovieRepository) :
    CatalogueMovieUseCase {
    override fun getAllMovie() = catalogueMovieRepository.getAllMovie()

    override fun getAllTv() = catalogueMovieRepository.getAllTv()

    override fun getDetailMovie(id: Int) = catalogueMovieRepository.getDetailMovie(id)

    override fun getDetailTv(id: Int) = catalogueMovieRepository.getDetailTv(id)

    override fun getMovieFavourites() = catalogueMovieRepository.getMovieFavourites()

    override fun setMovieFavourites(movie: Movie, state: Boolean) =
        catalogueMovieRepository.setMovieFavourites(movie, state)

    override fun getTvFavourites(): Flow<List<Tv>> = catalogueMovieRepository.getTvFavourites()

    override fun setTvFavourites(tv: Tv, state: Boolean) =
        catalogueMovieRepository.setTvFavourites(tv, state)
}