package com.voicelearn.app.api.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("imageUrl")
    val imageUrl: String? = null,

    @SerializedName("date")
    val date: String? = null,

    @SerializedName("writerBy")
    val writerBy: String? = null,

    @SerializedName("videoUrl")
    val videoUrl: String? = null
) : Parcelable