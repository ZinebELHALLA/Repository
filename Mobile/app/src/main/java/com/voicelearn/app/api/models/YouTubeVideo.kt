package com.voicelearn.app.models

import com.google.gson.annotations.SerializedName

data class YouTubeVideo(
    @SerializedName("videoId")
    val videoId: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String,

    @SerializedName("description")
    val description: String? = null
)