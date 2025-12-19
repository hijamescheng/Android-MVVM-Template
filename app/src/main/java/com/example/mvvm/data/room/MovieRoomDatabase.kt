package com.example.mvvm.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class, ScreenResponseEntity::class], version = 1)
abstract class MovieRoomDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    abstract fun screenResponseDao(): ScreenResponseDao
}
