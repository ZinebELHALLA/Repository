package com.voicelearn.app.ui.english_learning

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.voicelearn.app.R
import com.voicelearn.app.adapter.VideoAdapter
import com.voicelearn.app.api.responses.Article
import com.voicelearn.app.databinding.ActivityEnglishLearningDetailBinding
import com.voicelearn.app.helper.ViewModelFactory
import com.voicelearn.app.models.YouTubeVideo
import com.voicelearn.app.ui.english_learning.view_model.EnglishLearningViewModel

class EnglishLearningDetailActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityEnglishLearningDetailBinding.inflate(layoutInflater)
    }

    private val viewModel: EnglishLearningViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.english_learning_page_title)

        val article = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(ARTICLE_EXTRA, Article::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(ARTICLE_EXTRA)
        }

        article?.let {
            loadArticle(it)
            setupVideosRecyclerView()
            viewModel.loadYouTubeVideos(it.title, it.id)
            observeVideos(it.id)
        }
    }

    private fun loadArticle(article: Article) {
        Glide.with(this)
            .load(article.imageUrl)
            .placeholder(R.drawable.vec_placeholder)
            .centerCrop()
            .into(binding.ivDetailImage)

        binding.tvDetailTitle.text = article.title
        binding.tvDetailContent.text = Html.fromHtml(
            article.content,
            Html.FROM_HTML_MODE_LEGACY
        )
    }

    private fun setupVideosRecyclerView() {
        binding.rvVideos.apply {
            layoutManager = LinearLayoutManager(this@EnglishLearningDetailActivity)
            setHasFixedSize(true)
        }
    }

    private fun observeVideos(articleId: Int) {
        viewModel.videosList.observe(this) { videosMap ->
            videosMap[articleId]?.let { videos ->
                setVideoAdapter(videos)
            }
        }
    }

    private fun setVideoAdapter(videos: List<YouTubeVideo>) {
        val adapter = VideoAdapter(videos) { video ->
            openYouTubeVideo(video.videoId)
        }
        binding.rvVideos.adapter = adapter
    }

    private fun openYouTubeVideo(videoId: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoId"))
        startActivity(intent)
    }

    companion object {
        const val ARTICLE_EXTRA = "article"
    }
}