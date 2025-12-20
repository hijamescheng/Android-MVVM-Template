package com.example.mvvm.data.retrofit

import com.example.mvvm.data.RemoteMovieDataSource
import com.example.mvvm.data.Row
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TMDBMovieDataSource
    @Inject
    constructor(
        val service: TMDBService,
        val accountId: Int,
    ) : RemoteMovieDataSource {
        override suspend fun getHomePage(): List<Row> {
            with(service) {
                val homeList = mutableListOf<Row>()
                homeList.add(
                    Row(
                        "Watchlist",
                        getWatchListMovies(accountId).results.map { it.toRowTitle() },
                    ),
                )
                homeList.add(
                    Row(
                        "Discover Movies",
                        getDiscoverMovies().results.map { it.toRowTitle() },
                    ),
                )
                homeList.add(
                    Row(
                        "Discover TV",
                        getDiscoverTvShows().results.map { it.toRowTitle() },
                    ),
                )
                homeList.add(
                    Row(
                        "Now Playing",
                        getNowPlayingMovies().results.map { it.toRowTitle() },
                    ),
                )
                homeList.add(
                    Row(
                        "Popular",
                        getPopularMovies().results.map { it.toRowTitle() },
                    ),
                )
                homeList.add(
                    Row(
                        "Upcoming",
                        getUpcomingMovies().results.map { it.toRowTitle() },
                    ),
                )
                return homeList
            }
        }
    }
