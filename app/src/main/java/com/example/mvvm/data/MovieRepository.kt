package com.example.mvvm.data

import com.example.mvvm.data.room.ScreenResponseEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import jakarta.inject.Named
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieRepository
    @Inject
    constructor(
        val localDataSource: LocalMovieDataSource,
        val remoteMovieDataSource: RemoteMovieDataSource,
        @Named("IODispatcher") val ioDispatcher: CoroutineDispatcher,
        @Named("DefaultDispatcher") val defaultDispatcher: CoroutineDispatcher,
        val moshi: Moshi,
    ) {
        private val adapter =
            moshi.adapter<List<Row>>(
                Types.newParameterizedType(List::class.java, Row::class.java),
            )

        fun observeHomePage(): Flow<Result<List<Row>>> {
            refreshHomepage()
            return observeCachedHomePage()
        }

        // fire and forget -> fetch from remote API and cache result in local storage
        fun refreshHomepage() {
            CoroutineScope(ioDispatcher).launch {
                runCatching {
                    remoteMovieDataSource.getHomePage()
                }.onSuccess {
                    localDataSource.upsertScreenResponse(
                        ScreenResponseEntity(
                            screenName = HOME_SCREEN,
                            responseJson = adapter.toJson(it),
                            updatedAt = System.currentTimeMillis(),
                        ),
                    )
                }.onFailure {
                    // Fail silently
                    print("")
                }
            }
        }

        fun observeCachedHomePage(): Flow<Result<List<Row>>> {
            // treat local storage as single source of truth
            // 1. query cached home screen response
            // 2. start a async request to fetch and cache from remote api
            return localDataSource.getScreenResponse(HOME_SCREEN).map { queryResult ->
                queryResult?.let {
                    Result.success(adapter.fromJson(it).orEmpty())
                } ?: Result.success(emptyList())
            }.flowOn(defaultDispatcher)
        }

        companion object {
            const val HOME_SCREEN = "home"
        }
    }
