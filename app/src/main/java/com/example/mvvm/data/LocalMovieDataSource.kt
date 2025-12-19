package com.example.mvvm.data

import com.example.mvvm.data.room.ScreenResponseEntity
import kotlinx.coroutines.flow.Flow

interface LocalMovieDataSource {
    fun getScreenResponse(screenName: String): Flow<String?>

    fun upsertScreenResponse(entity: ScreenResponseEntity)
}
