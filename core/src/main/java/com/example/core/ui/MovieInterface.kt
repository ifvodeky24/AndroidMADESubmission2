package com.example.core.ui

import com.example.core.domain.model.Movie

interface MovieInterface {
    fun click(movie: Movie)
    fun shareClick(movie: Movie)
}