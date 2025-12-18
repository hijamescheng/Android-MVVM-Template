package com.example.mvvm.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScreenResponseEntity(
    @PrimaryKey val screenName: String,
    @ColumnInfo("responseJson") val responseJson: String,
    @ColumnInfo("updated_at") val updatedAt: Long,
)
