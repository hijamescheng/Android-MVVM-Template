package com.example.mvvm.data.room

import com.example.mvvm.data.LocalMovieDataSource
import javax.inject.Inject

class RoomMovieDataSource
    @Inject
    constructor(
        val db: MovieRoomDatabase,
    ) : LocalMovieDataSource {
        override suspend fun getMovies(): List<MovieEntity> = db.movieDao().getAll()

        override suspend fun getScreenResponse(screenName: String): ScreenResponseEntity? =
            db.screenResponseDao().getScreenResponseByName(screenName)
    }
