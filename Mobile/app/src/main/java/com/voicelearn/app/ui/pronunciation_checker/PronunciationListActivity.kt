package com.voicelearn.app.ui.pronunciation_checker

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.voicelearn.app.R
import com.voicelearn.app.adapter.ItemAdapter
import com.voicelearn.app.api.responses.Quiz
import com.voicelearn.app.databinding.ActivityPronunciationListBinding
import com.voicelearn.app.helper.ViewModelFactory
import com.voicelearn.app.ui.pronunciation_checker.view_model.PronunciationListViewModel

class PronunciationListActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPronunciationListBinding.inflate(layoutInflater)
    }
    private val viewModel: PronunciationListViewModel by viewModels {
        ViewModelFactory(this)
    }

    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val stringAB: String = resources.getString(R.string.pronunciation_page_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = stringAB

        val level = intent.getStringExtra(LEVEL_EXTRA)
        level?.let {
            viewModel.getPronunciationListByLevel(it)
        }

        viewModel.pronunciationList.observe(this) {
            observeAdapter(it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun observeAdapter(quizzes: List<Quiz>) {
        adapter = ItemAdapter(this@PronunciationListActivity, quizzes)
        binding.apply {
            rcPronunciationList.setHasFixedSize(true)
            rcPronunciationList.adapter = adapter
            rcPronunciationList.layoutManager = LinearLayoutManager(this@PronunciationListActivity)
        }
        // Inside your adapter's onItemClick callback
        adapter.setOnItemClickCallback(object : ItemAdapter.OnItemClickCallback {
            override fun onItemClicked(id: String) {
                val selectedQuiz = quizzes.find { it.id.toString() == id }
                Intent(this@PronunciationListActivity, PronunciationSubmitActivity::class.java).also { intent ->
                    intent.putExtra(PronunciationSubmitActivity.EXTRA_ID, id)
                    intent.putExtra(PronunciationSubmitActivity.EXTRA_QUIZ, selectedQuiz) // Passing actual Quiz object
                    intent.putExtra(PronunciationSubmitActivity.EXTRA_WORD, selectedQuiz?.text) // Get the text safely
                    startActivity(intent)
                }
            }
        })
    }

    companion object {
        private const val LEVEL_EXTRA = "level"
    }
}