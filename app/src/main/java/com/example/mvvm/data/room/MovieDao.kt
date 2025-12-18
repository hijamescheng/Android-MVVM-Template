package com.example.mvvm.data.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface MovieDao {
    @Query("SELECT * FROM movieentity")
    suspend fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM movieentity WHERE id = :userId")
    suspend fun getMovieById(userId: Int): MovieEntity?
}
