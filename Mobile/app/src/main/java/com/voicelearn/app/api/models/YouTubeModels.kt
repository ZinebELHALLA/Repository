package com.voicelearn.app.api.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: Int,
    val title: String,
    val content: String,
    val imageUrl: String? = null,
    val videoUrl: String? = null
) : Parcelable

data class PlaylistItemsResponse(
    @SerializedName("items") val items: List<PlaylistItem>,
    @SerializedName("nextPageToken") val nextPageToken: String?,
    @SerializedName("pageInfo") val pageInfo: PageInfo
)

data class PlaylistItem(
    @SerializedName("id") val id: String,
    @SerializedName("snippet") val snippet: VideoSnippet
)

data class VideoSnippet(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("thumbnails") val thumbnails: Thumbnails,
    @SerializedName("resourceId") val resourceId: ResourceId
)

data class Thumbnails(
    @SerializedName("medium") val medium: Thumbnail
)

data class Thumbnail(
    @SerializedName("url") val url: String
)

data class ResourceId(
    @SerializedName("videoId") val videoId: String
)

data class PageInfo(
    @SerializedName("totalResults") val totalResults: Int
)