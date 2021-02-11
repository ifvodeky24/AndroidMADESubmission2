package com.example.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.CatalogueMovieUseCase

class FavouriteViewModel(private val catalogueMovieUseCase: CatalogueMovieUseCase) : ViewModel() {

    fun getTvFavourites() =
        catalogueMovieUseCase.getTvFavourites().asLiveData()

    fun getMovieFavourites() =
        catalogueMovieUseCase.getMovieFavourites().asLiveData()
}