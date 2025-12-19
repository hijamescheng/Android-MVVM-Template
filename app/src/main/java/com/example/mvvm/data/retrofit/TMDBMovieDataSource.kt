package com.example.mvvm.data.retrofit

import com.example.mvvm.data.RemoteMovieDataSource
import com.example.mvvm.data.Row
import javax.inject.Inject

class TMDBMovieDataSource
    @Inject
    constructor(
        val service: TMDBService,
        val accountId: Int,
    ) : RemoteMovieDataSource {
        override fun getHomePage(): List<Row> {
            with(service) {
                val homeList = mutableListOf<Row>()
                homeList.add(
                    Row(
                        "Watchlist",
                        getWatchListMovies(accountId).map { it.toRowTitle() },
                    ),
                )
                homeList.add(
                    Row(
                        "Discover Movies",
                        getDiscoverMovies().map { it.toRowTitle() },
                    ),
                )
                homeList.add(
                    Row(
                        "Discover TV",
                        getDiscoverTvShows().map { it.toRowTitle() },
                    ),
                )
                homeList.add(
                    Row(
                        "Now Playing",
                        getNowPlayingMovies().map { it.toRowTitle() },
                    ),
                )
                homeList.add(
                    Row(
                        "Popular",
                        getPopularMovies().map { it.toRowTitle() },
                    ),
                )
                homeList.add(
                    Row(
                        "Upcoming",
                        getUpcomingMovies().map { it.toRowTitle() },
                    ),
                )
                return homeList
            }
        }
    }
