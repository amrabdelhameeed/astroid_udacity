package com.example.astroid_udacity
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
@Entity(tableName = "picture_table")
data class PictureOfDay(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @Json(name = "media_type") val mediaType: String, val title: String,
    val url: String)