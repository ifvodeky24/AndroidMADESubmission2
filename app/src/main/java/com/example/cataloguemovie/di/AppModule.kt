package com.example.core.di

import com.example.cataloguemovie.movie.MovieViewModel
import com.example.cataloguemovie.tv.TvViewModel
import com.example.core.domain.usecase.CatalogueMovieInteractor
import com.example.core.domain.usecase.CatalogueMovieUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<CatalogueMovieUseCase> { CatalogueMovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvViewModel(get()) }
}