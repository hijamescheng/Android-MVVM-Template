package com.example.mvvm.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface TMDBService {
    @RequiresAuth
    @GET("account/{account_id}/watchlist/movies")
    suspend fun getWatchListMovies(
        @Path("account_id") accountId: Int,
    ): WatchlistMovieResponse

    @RequiresAuth
    @GET("discover/movie")
    suspend fun getDiscoverMovies(): WatchlistMovieResponse

    @RequiresAuth
    @GET("discover/tv")
    suspend fun getDiscoverTvShows(): WatchlistMovieResponse

    @RequiresAuth
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): WatchlistMovieResponse

    @RequiresAuth
    @GET("movie/popular")
    suspend fun getPopularMovies(): WatchlistMovieResponse

    @RequiresAuth
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): WatchlistMovieResponse
}
