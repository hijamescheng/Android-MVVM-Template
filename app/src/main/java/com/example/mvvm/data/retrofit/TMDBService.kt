package com.example.mvvm.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface TMDBService {
    @GET("https://api.themoviedb.org/3/account/{account_id}/rated/movies")
    fun getRatedMovies(
        @Path("account_id") accountId: Int,
    ): List<MovieDto>
}
