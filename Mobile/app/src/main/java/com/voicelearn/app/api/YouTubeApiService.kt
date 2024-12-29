package com.voicelearn.app.api

import com.voicelearn.app.api.models.PlaylistItemsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApiService {
    @GET("youtube/v3/playlistItems")
    suspend fun getPlaylistItems(
        @Query("part") part: String = "snippet",
        @Query("playlistId") playlistId: String,
        @Query("maxResults") maxResults: Int = 50,
        @Query("key") apiKey: String = "AIzaSyCkio6lVQHD8QkeVfbwSTsALFovMDP10Ag"
    ): Response<PlaylistItemsResponse>
}