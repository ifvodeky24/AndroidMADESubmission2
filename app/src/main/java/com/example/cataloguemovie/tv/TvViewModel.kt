package com.example.cataloguemovie.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.model.Tv
import com.example.core.domain.usecase.CatalogueMovieUseCase

class TvViewModel(private val catalogueMovieUseCase: CatalogueMovieUseCase) : ViewModel() {

    fun getAllTv() = catalogueMovieUseCase.getAllTv().asLiveData()

    fun getDetailTv(id: Int) =
        catalogueMovieUseCase.getDetailTv(id).asLiveData()

    fun setBookmark(tv: Tv) {
        val newState = !tv.favorite
        catalogueMovieUseCase.setTvFavourites(tv, newState)
    }
}