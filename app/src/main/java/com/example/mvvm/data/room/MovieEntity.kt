package com.example.mvvm.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo("adult") val adult: Boolean,
    @ColumnInfo("backdrop_path") val backdrop_path: String?,
    @ColumnInfo("genre_ids") val genre_ids: String?,
    @ColumnInfo("original_language") val original_language: String?,
    @ColumnInfo("original_title") val original_title: String?,
    @ColumnInfo("overview") val overview: String?,
    @ColumnInfo("popularity") val popularity: Double = 0.0,
    @ColumnInfo("poster_path") val poster_path: String?,
    @ColumnInfo("release_date") val release_date: String?,
    @ColumnInfo("title") val title: String?,
    @ColumnInfo("video") val video: Boolean,
    @ColumnInfo("vote_average") val vote_average: Double? = 0.0,
    @ColumnInfo("vote_count") val vote_count: Int? = 0,
    @ColumnInfo("rating") val rating: Int? = 0,
)
