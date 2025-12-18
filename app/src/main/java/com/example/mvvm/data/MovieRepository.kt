package com.example.mvvm.data

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MovieRepository
    @Inject
    constructor(
        val localDataSource: LocalMovieDataSource,
        val remoteMovieDataSource: RemoteMovieDataSource,
        val dispatcher: CoroutineDispatcher,
    )
