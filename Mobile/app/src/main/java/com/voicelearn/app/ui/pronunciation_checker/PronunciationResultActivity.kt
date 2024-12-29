package com.voicelearn.app.ui.pronunciation_checker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.voicelearn.app.databinding.ActivityPronunciationResultBinding


class PronunciationResultActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPronunciationResultBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Getting data from intent
        val status = intent.getStringExtra("status")
        val word = intent.getStringExtra("word")
        val recognizedText = intent.getStringExtra("recognized") // Get recognized text

        Log.d("PronunciationResultActivity", "Status: $status, Word: $word, Recognized: $recognizedText")

        binding.tvPRWord.text = word ?: "Unknown Word"
        binding.tvPRStatus.text = status ?: "Status Unknown"

        if (status == "Perfect") {
            binding.tvPRAnswer.text = "$recognizedText \n\n pronounced perfectly!" // Use the recognized text
        } else {
            binding.tvPRAnswer.text = "$recognizedText \n\n pronounced incorrectly." // Use the recognized text
        }

        // Setting up buttons' click listeners
        binding.btnPRTry.setOnClickListener {
            // Go back to the pronunciation submission activity
            // You might want to pass the same word back if needed
            val intent = Intent(this, PronunciationSubmitActivity::class.java)
            intent.putExtra(PronunciationSubmitActivity.EXTRA_WORD, word) // Pass the word back
            startActivity(intent)
            finish() // Optional: call finish if you don't want to keep this activity in the back stack
        }

        binding.btnPRNext.setOnClickListener {
            // Handle going back to the word selection or next word
            // Assuming you have a WordSelectionActivity or similar
            val intent = Intent(this, PronunciationListActivity::class.java) // Update this line to your actual activity
            startActivity(intent)
            finish() // Optional: call finish if you don't want to keep this activity in the back stack
        }
    }
}