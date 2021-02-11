package com.example.core.ui

import com.example.core.domain.model.Tv

interface TvInterface {
    fun click(tv: Tv)
    fun shareClick(tv: Tv)
}