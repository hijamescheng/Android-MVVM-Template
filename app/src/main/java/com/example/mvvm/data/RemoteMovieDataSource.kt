package com.example.mvvm.data

import com.example.mvvm.data.retrofit.MovieDto

interface RemoteMovieDataSource {
    fun getMovies(): List<MovieDto>
}
