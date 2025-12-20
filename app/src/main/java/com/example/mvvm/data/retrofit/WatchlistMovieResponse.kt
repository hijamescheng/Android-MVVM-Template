package com.example.mvvm.data.retrofit

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WatchlistMovieResponse(
    val results: List<TitleDto>,
)
