package com.voicelearn.app.ui.pronunciation_checker.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.voicelearn.app.api.responses.ResponsePronunciationPredict
import com.voicelearn.app.data.Repository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
class PronunciationSubmitViewModel(private val repository: Repository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    private val _isSuccess = MutableLiveData<Boolean>()
    private val _status = MutableLiveData<String>()
    private val _recognizedText = MutableLiveData<String>() // New LiveData for recognized text

    val isLoading: LiveData<Boolean> = _isLoading
    val isSuccess: LiveData<Boolean> = _isSuccess
    val status: LiveData<String> = _status
    val recognizedText: LiveData<String> = _recognizedText // Expose recognized text

    fun postAnswer(id: Int, fileAudio: File) {
        _isLoading.value = true
        val requestAudioFile = fileAudio.asRequestBody("audio/wav".toMediaType())
        val audioMultipart = MultipartBody.Part.createFormData("file", fileAudio.name, requestAudioFile)

        repository.checkPronunciation(id, audioMultipart)
            .enqueue(object : Callback<ResponsePronunciationPredict> {
                override fun onResponse(call: Call<ResponsePronunciationPredict>, response: Response<ResponsePronunciationPredict>) {
                    val responseBody = response.body()
                    if (response.isSuccessful) {
                        _isSuccess.value = true
                        responseBody?.status?.let { _status.value = it }
                        responseBody?.recognizedText?.let { _recognizedText.value = it } // Capture recognized text from new field
                    } else {
                        Log.e("Pronunciation Error", "Code: ${response.code()}, Message: ${response.errorBody()?.string()}")
                        _isSuccess.value = false
                    }
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<ResponsePronunciationPredict>, t: Throwable) {
                    _isLoading.value = false
                }
            })
    }
}