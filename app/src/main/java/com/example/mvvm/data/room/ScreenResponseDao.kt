package com.example.mvvm.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScreenResponseDao {
    @Query("SELECT responseJson FROM screenresponseentity where screenName = :screenName")
    fun getScreenResponseByName(screenName: String): Flow<String?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(entity: ScreenResponseEntity)
}
