package com.example.mvvm.data

interface RemoteMovieDataSource {
    suspend fun getHomePage(): List<Row>
}
