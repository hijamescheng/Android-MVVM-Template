package com.example.mvvm.data

interface RemoteMovieDataSource {
    fun getHomePage(): List<Row>
}
