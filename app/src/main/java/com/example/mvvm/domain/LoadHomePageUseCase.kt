package com.example.mvvm.domain

import com.example.mvvm.data.MovieRepository
import com.example.mvvm.data.Row
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadHomePageUseCase
    @Inject
    constructor(
        val repository: MovieRepository,
    ) {
        fun execute(): Flow<Result<List<Row>>> = repository.observeHomePage()
    }
