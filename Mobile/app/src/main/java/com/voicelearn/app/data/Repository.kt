package com.voicelearn.app.data

import android.content.SharedPreferences
import com.voicelearn.app.api.ApiService
import com.voicelearn.app.api.requests.LoginRequest
import com.voicelearn.app.api.requests.RegisterRequest
import com.voicelearn.app.api.responses.ResponseEnglishLearning
import com.voicelearn.app.api.responses.ResponseLogin
import com.voicelearn.app.api.responses.ResponsePronunciationPredict
import com.voicelearn.app.api.responses.ResponseQuizList
import com.voicelearn.app.api.responses.ResponseRegister
import okhttp3.MultipartBody
import retrofit2.Call

class Repository(
    private val apiService: ApiService,
    private val sharedPreferences: SharedPreferences
) {
    fun login(loginRequest: LoginRequest): Call<ResponseLogin> {
        return apiService.login(loginRequest)
    }

    fun register(registerRequest: RegisterRequest): Call<ResponseRegister> {
        return apiService.register(registerRequest)
    }

    fun getSpellingListByLevel(level: String): Call<ResponseQuizList> {
        return apiService.getSpellingListByLevel(level)
    }

    fun getPronunciationListByLevel(level: String): Call<ResponseQuizList> {
        return apiService.getPronunciationListByLevel(level)
    }

    fun getArticleList(): Call<ResponseEnglishLearning> {
        return apiService.getArticleList()
    }

    fun checkPronunciation(id: Int, file: MultipartBody.Part): Call<ResponsePronunciationPredict> {
        // Call the API without using token in Headers
        return apiService.checkPronunciation(file, id)
    }

    fun logout() {
        // Clear shared preferences (if needed)
        sharedPreferences.edit().remove(USERNAME_KEY).apply()
    }

    fun getSignedInUserName(): String? {
        return sharedPreferences.getString(USERNAME_KEY, null)
    }

    fun saveSignedInUserName(userName: String) {
        sharedPreferences.edit().putString(USERNAME_KEY, userName).apply()
    }

    companion object {
        private const val USERNAME_KEY = "signed_in_user"

        @Volatile
        private var instance: Repository? = null

        fun getInstance(
            apiService: ApiService,
            sharedPreferences: SharedPreferences
        ): Repository {
            return instance ?: synchronized(this) {
                instance = Repository(apiService, sharedPreferences)
                instance as Repository
            }
        }
    }
}