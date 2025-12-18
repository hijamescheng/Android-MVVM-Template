package com.example.mvvm.data.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ScreenResponseDao {
    @Query("SELECT * FROM screenresponseentity where screenName = :screenName")
    suspend fun getScreenResponseByName(screenName: String): ScreenResponseEntity?
}
