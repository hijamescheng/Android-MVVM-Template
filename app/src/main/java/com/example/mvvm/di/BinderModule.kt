package com.example.mvvm.di

import com.example.mvvm.data.LocalMovieDataSource
import com.example.mvvm.data.RemoteMovieDataSource
import com.example.mvvm.data.retrofit.TMDBMovieDataSource
import com.example.mvvm.data.room.RoomMovieDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BinderModule {
    @Binds
    abstract fun bindLocalMovieDataSource(roomMovieDataSource: RoomMovieDataSource): LocalMovieDataSource

    @Binds
    abstract fun bindRemoteMovieDataSource(tmdbMovieDataSource: TMDBMovieDataSource): RemoteMovieDataSource
}
