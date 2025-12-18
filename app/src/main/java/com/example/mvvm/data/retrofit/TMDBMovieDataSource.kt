package com.example.mvvm.data.retrofit

import com.example.mvvm.data.RemoteMovieDataSource
import javax.inject.Inject

class TMDBMovieDataSource
    @Inject
    constructor(
        val service: TMDBService,
        val accountId: Int,
    ) : RemoteMovieDataSource {
        override fun getMovies(): List<MovieDto> = service.getRatedMovies(accountId)
    }
