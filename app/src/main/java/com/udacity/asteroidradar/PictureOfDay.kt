package com.udacity.asteroidradar

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
@Parcelize
@JsonClass(generateAdapter = true)
data class PictureOfDay(val date: String, @Json(name = "media_type") val mediaType: String, val title: String,
                        @PrimaryKey  val url: String): Parcelable

