package com.example.favourite.di

import com.example.favourite.FavouriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavouriteViewModel(get()) }
}