package com.example.mvvm.data

import com.example.mvvm.data.room.MovieEntity
import com.example.mvvm.data.room.ScreenResponseEntity

interface LocalMovieDataSource {
    suspend fun getMovies(): List<MovieEntity>

    suspend fun getScreenResponse(screenName: String): ScreenResponseEntity?
}
