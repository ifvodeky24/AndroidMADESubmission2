package com.example.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.local.entity.TvEntity

@Database(entities = [MovieEntity::class, TvEntity::class], version = 1, exportSchema = false)
abstract class CatalogueMovieDatabase : RoomDatabase() {
    abstract fun catalogueMovieDao(): CatalogueMovieDao
}