package com.voicelearn.app.ui.pronunciation_checker

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import android.Manifest
import android.content.ContextWrapper
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.voicelearn.app.api.responses.Quiz
import com.voicelearn.app.databinding.ActivityPronunciationSubmitBinding
import com.voicelearn.app.helper.ViewModelFactory
import com.voicelearn.app.ui.pronunciation_checker.view_model.PronunciationSubmitViewModel
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PronunciationSubmitActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private val binding by lazy {
        ActivityPronunciationSubmitBinding.inflate(layoutInflater)
    }
    private val viewModel: PronunciationSubmitViewModel by viewModels {
        ViewModelFactory(this)
    }
    private val textToSpeech by lazy {
        TextToSpeech(this@PronunciationSubmitActivity, this)
    }

    private var mediaRecorder: MediaRecorder? = null
    private lateinit var mediaPlayer: MediaPlayer

    private var quiz: Quiz? = null
    private var fileAudio: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Retrieve the Quiz object from the Intent
        quiz = intent.getParcelableExtra(EXTRA_QUIZ)

        // Log the retrieved quiz object for debugging
        Log.d("PronunciationSubmitActivity", "Retrieved Quiz: $quiz")

        // Get the passed word from the intent
        val selectedWord = intent.getStringExtra(EXTRA_WORD)

        // Set the selected word to a TextView for pronunciation
        binding.tvPSWord.text = selectedWord ?: "Word not found"

        // Additional initialization code
        setSpeakWords(selectedWord.toString())
        setHide()
        setAction()
        observeViewModel()

        if (isMicrophonePresent()) {
            getMicrophonePermission()
        }
    }

    private fun observeViewModel() {
        // Inside observeViewModel
        viewModel.isSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                val selectedWord = intent.getStringExtra(EXTRA_WORD)

                viewModel.status.observe(this) { status ->
                    viewModel.recognizedText.observe(this) { recognizedText ->
                        Intent(this@PronunciationSubmitActivity, PronunciationResultActivity::class.java).also { intent ->
                            intent.putExtra("status", status)
                            intent.putExtra("word", selectedWord)
                            intent.putExtra("recognized", recognizedText) // Pass the recognized text
                            startActivity(intent)
                        }
                        finish()
                    }
                }
            }
        }
    }

    private fun getMicrophonePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), MICROPHONE_PERMISSION_CODE)
        }
    }


    private fun isMicrophonePresent(): Boolean {
        return packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.ENGLISH)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Initialization failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }

    private fun setSpeakWords(speak: String) {
        binding.apply {
            ivPSSpeak.setOnClickListener {
                val text = speak
                if (text.isNotEmpty()) {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                }
            }
        }
    }

    private fun setAction() {
        binding.apply {
            btnPSRecord.setOnClickListener {
                mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    MediaRecorder(this@PronunciationSubmitActivity)
                } else {
                    MediaRecorder()
                }

                mediaRecorder?.let {
                    it.apply {
                        setAudioSource(MediaRecorder.AudioSource.MIC)
                        setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP) // Use a compatible format
                        setOutputFile(getRecordingPath())
                        setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // You could use a different encoder
                    }

                    try {
                        it.prepare()
                        it.start()
                        Toast.makeText(this@PronunciationSubmitActivity, "Recording started...", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                btnPlay.isEnabled = false
                btnStopRecord.isEnabled = true
            }

            btnStopRecord.setOnClickListener {
                mediaRecorder?.stop()
                mediaRecorder?.release()
                mediaRecorder = null

                btnPlay.isEnabled = true
                btnPSRecord.isEnabled = false
                setVisible()

                Toast.makeText(this@PronunciationSubmitActivity, "Recording stopped...", Toast.LENGTH_SHORT).show()
            }
            btnPSSubmit.setOnClickListener {
                Log.d("PronunciationSubmitActivity", "Quiz: $quiz, File Exists: ${fileAudio?.exists()}, File: $fileAudio")
                if (quiz != null && fileAudio != null && fileAudio!!.exists()) {
                    quiz?.id?.let { id ->
                        viewModel.postAnswer(id, fileAudio!!)
                    }
                } else {
                    Toast.makeText(this@PronunciationSubmitActivity, "Please record your pronunciation first!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getRecordingPath(): String {
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd_hh.mm.ss", Locale.getDefault())
        val dateFormat = simpleDateFormat.format(Date())
        val musicDirectory = ContextWrapper(applicationContext).getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val file = File(musicDirectory, "linguity$dateFormat.wav")
        fileAudio = file // Assign to the class property

        // Create the file if it does not exist
        try {
            if (!file.exists()) {
                file.createNewFile()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error creating file", Toast.LENGTH_SHORT).show()
        }

        Log.d("Path", "Recording path: ${file.absolutePath}") // Log the path to see where it is saved.
        return file.absolutePath
    }

    private fun setHide() {
        binding.apply {
            cvPSSubmit.isVisible = false
        }
    }

    private fun setVisible() {
        binding.apply {
            cvPSSubmit.isVisible = true
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_QUIZ = "quiz"
        const val EXTRA_WORD = "word" // New line: Constant for the word passed
        const val EXTRA_REC = "recognized_text"

        private const val MICROPHONE_PERMISSION_CODE = 200
    }
}