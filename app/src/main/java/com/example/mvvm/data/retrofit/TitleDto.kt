package com.example.mvvm.data.retrofit

import com.example.mvvm.data.RowTitle

data class TitleDto(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val rating: Int,
)

fun TitleDto.toRowTitle(): RowTitle {
    return RowTitle(
        id = id,
        title = title,
        imageUrl = poster_path,
    )
}
