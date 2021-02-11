package com.example.cataloguemovie.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.model.Movie
import com.example.core.domain.usecase.CatalogueMovieUseCase

class MovieViewModel(private val catalogueMovieUseCase: CatalogueMovieUseCase) : ViewModel() {

    fun getAllMovie() =
        catalogueMovieUseCase.getAllMovie().asLiveData()

    fun getDetailMovie(id: Int) =
        catalogueMovieUseCase.getDetailMovie(id).asLiveData()

    fun setBookmark(movie: Movie) {
        val newState = !movie.favorite
        catalogueMovieUseCase.setMovieFavourites(movie, newState)
    }

}