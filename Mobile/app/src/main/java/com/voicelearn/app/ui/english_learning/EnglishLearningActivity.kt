package com.voicelearn.app.ui.english_learning

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.voicelearn.app.R
import com.voicelearn.app.adapter.ArticleAdapter
import com.voicelearn.app.api.responses.Article
import com.voicelearn.app.databinding.ActivityEnglishLearningBinding
import com.voicelearn.app.helper.ViewModelFactory
import com.voicelearn.app.ui.english_learning.view_model.EnglishLearningViewModel

class EnglishLearningActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityEnglishLearningBinding.inflate(layoutInflater)
    }
    private val viewModel: EnglishLearningViewModel by viewModels {
        ViewModelFactory(this)
    }
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val stringAB: String = resources.getString(R.string.english_learning_page_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = stringAB

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.rvArticle.apply {
            layoutManager = LinearLayoutManager(this@EnglishLearningActivity)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun observeViewModel() {
        viewModel.articleList.observe(this) { articles ->
            setDataAdapter(articles)
        }
        viewModel.isLoading.observe(this) {
            binding.cvLoading.isVisible = it
        }
    }

    private fun setDataAdapter(list: List<Article>) {
        articleAdapter = ArticleAdapter(list)
        binding.rvArticle.adapter = articleAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}