package com.example.mvvm.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface TMDBService {
    @RequiresAuth
    @GET("/account/{account_id}/watchlist/movies")
    fun getWatchListMovies(
        @Path("account_id") accountId: Int,
    ): List<TitleDto>

    @RequiresAuth
    @GET("/discover/movie")
    fun getDiscoverMovies(): List<TitleDto>

    @RequiresAuth
    @GET("/discover/tv")
    fun getDiscoverTvShows(): List<TitleDto>

    @RequiresAuth
    @GET("/movie/now_playing")
    fun getNowPlayingMovies(): List<TitleDto>

    @RequiresAuth
    @GET("/movie/popular")
    fun getPopularMovies(): List<TitleDto>

    @RequiresAuth
    @GET("/movie/upcoming")
    fun getUpcomingMovies(): List<TitleDto>
}
