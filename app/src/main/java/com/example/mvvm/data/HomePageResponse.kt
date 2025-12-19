package com.example.mvvm.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Row(val header: String, val titleList: List<RowTitle>)

@JsonClass(generateAdapter = true)
data class RowTitle(val id: Int, val title: String, val imageUrl: String)
