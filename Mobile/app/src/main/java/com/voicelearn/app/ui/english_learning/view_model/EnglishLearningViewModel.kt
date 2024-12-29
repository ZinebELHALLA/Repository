package com.voicelearn.app.ui.english_learning.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voicelearn.app.api.responses.Article
import com.voicelearn.app.data.Repository
import com.voicelearn.app.models.YouTubeVideo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EnglishLearningViewModel(private val repository: Repository) : ViewModel() {
    private val _articleList = MutableLiveData<List<Article>>()
    private val _toastText = MutableLiveData<String>()
    private val _isLoading = MutableLiveData<Boolean>()
    // Spécifier explicitement les types non-null pour la Map
    private val _videosList = MutableLiveData<Map<Int, List<YouTubeVideo>>>()

    val articleList: LiveData<List<Article>> = _articleList
    val toastText: LiveData<String> = _toastText
    val isLoading: LiveData<Boolean> = _isLoading
    val videosList: LiveData<Map<Int, List<YouTubeVideo>>> = _videosList

    init {
        getArticleList()
    }

    private fun getArticleList() {
        _isLoading.value = true

        val mockArticles = listOf(
            Article(
                id = 1,
                title = "English Grammar Basics",
                content = "Master essential English grammar concepts...",
            ),
            Article(
                id = 2,
                title = "English Conversation Skills",
                content = "Practice everyday English conversations...",
            ),
            Article(
                id = 3,
                title = "Business English Essentials",
                content = "Learn professional English for workplace...",
            )
        )

        _articleList.value = mockArticles
        loadVideosForArticles(mockArticles)
        _isLoading.value = false
    }

    private fun loadVideosForArticles(articles: List<Article>) {
        viewModelScope.launch {
            try {
                // Utiliser LinkedHashMap pour préserver l'ordre d'insertion
                val videosMap = LinkedHashMap<Int, List<YouTubeVideo>>()

                articles.forEach { article ->
                    // S'assurer que l'ID de l'article n'est pas null avant de l'utiliser comme clé
                    val articleId = article.id
                    if (articleId != null) {
                        val mockVideos = when (articleId) {
                            1 -> listOf(
                                YouTubeVideo(
                                    videoId = "abc123",
                                    title = "Basic English Grammar - Part 1",
                                    thumbnailUrl = "https://i.ytimg.com/vi/QXVzmzhxWWc/mqdefault.jpg"
                                ),
                                YouTubeVideo(
                                    videoId = "def456",
                                    title = "English Grammar Rules",
                                    thumbnailUrl = "https://example.com/thumbnail2.jpg"
                                )
                            )
                            2 -> listOf(
                                YouTubeVideo(
                                    videoId = "ghi789",
                                    title = "Daily English Conversations",
                                    thumbnailUrl = "https://example.com/thumbnail3.jpg"
                                )
                            )
                            3 -> listOf(
                                YouTubeVideo(
                                    videoId = "jkl012",
                                    title = "Business English Tutorial",
                                    thumbnailUrl = "https://example.com/thumbnail4.jpg"
                                )
                            )
                            else -> emptyList()
                        }
                        videosMap[articleId] = mockVideos
                    }
                }
                _videosList.value = videosMap
            } catch (e: Exception) {
                _toastText.value = "Erreur lors du chargement des vidéos: ${e.message}"
            }
        }
    }

    // Méthode pour obtenir les vidéos d'un article spécifique
    fun getVideosForArticle(articleId: Int): List<YouTubeVideo> {
        return _videosList.value?.get(articleId) ?: emptyList()
    }

    fun loadYouTubeVideos(articleTitle: String, articleId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val videos = fetchYouTubeVideos(articleTitle)
                val currentVideos = _videosList.value?.toMutableMap() ?: mutableMapOf()
                currentVideos[articleId] = videos
                _videosList.value = currentVideos
            } catch (e: Exception) {
                _toastText.value = "Erreur lors du chargement des vidéos YouTube: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun fetchYouTubeVideos(query: String): List<YouTubeVideo> {
        delay(1000) // Simuler un délai réseau
        return listOf(
            YouTubeVideo(
                videoId = "sample1",
                title = "Video about $query",
                thumbnailUrl = "https://example.com/thumb1.jpg"
            )
        )
    }
}