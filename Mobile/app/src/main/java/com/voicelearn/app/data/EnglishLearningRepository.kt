package com.voicelearn.app.data

import com.voicelearn.app.api.YouTubeApiService
import com.voicelearn.app.api.responses.Article

class EnglishLearningRepository(private val youtubeApi: YouTubeApiService) {
    companion object {
        const val GRAMMAR_PLAYLIST = "PL3j44Z9ZgXlYalgMQqQ9we9YJQmZu2vYB"
        const val CONVERSATION_PLAYLIST = "PLrA7GBJxRqJDkwZRGOJA4tsKoQhGAE-F9"
        const val BUSINESS_PLAYLIST = "PLrA7GBJxRqJB2kxDD6J9lFmLEVyS5aQGS"
    }

    suspend fun getEnglishLessons(playlistId: String): Result<List<Article>> {
        return try {
            val response = youtubeApi.getPlaylistItems(playlistId = playlistId)
            if (response.isSuccessful) {
                val lessons = response.body()?.items?.map { item ->
                    Article(
                        id = item.id.hashCode(),
                        title = cleanTitle(item.snippet.title),
                        content = item.snippet.description,
                        imageUrl = item.snippet.thumbnails.medium.url,
                        videoUrl = "https://www.youtube.com/watch?v=${item.snippet.resourceId.videoId}"
                    )
                } ?: emptyList()
                Result.success(lessons)
            } else {
                Result.failure(Exception("Failed to fetch lessons: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun cleanTitle(title: String): String {
        return title.replace(Regex("\\([^)]\\)|\\[[^]]\\]"), "").trim()
    }
}