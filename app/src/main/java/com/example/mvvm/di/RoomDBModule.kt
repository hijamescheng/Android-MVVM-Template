package com.example.mvvm.di

import android.content.Context
import androidx.room.Room
import com.example.mvvm.data.room.MovieRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Named
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object RoomDBModule {
    @Provides
    fun provideMovieRoomDB(
        @ApplicationContext context: Context,
    ): MovieRoomDatabase = Room.databaseBuilder(context, MovieRoomDatabase::class.java, "movie-db").build()

    @Provides
    @Named("IODispatcher")
    fun proviesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Named("DefaultDispatcher")
    fun proviesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
