package com.example.mvvm.data.room

import com.example.mvvm.data.LocalMovieDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomMovieDataSource
    @Inject
    constructor(
        val db: MovieRoomDatabase,
    ) : LocalMovieDataSource {
        override fun getScreenResponse(screenName: String): Flow<String?> =
            db.screenResponseDao().getScreenResponseByName(screenName)

        override fun upsertScreenResponse(entity: ScreenResponseEntity) {
            db.screenResponseDao().upsert(entity)
        }
    }
