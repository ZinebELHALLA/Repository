package com.voicelearn.app.api

import com.voicelearn.app.api.requests.LoginRequest
import com.voicelearn.app.api.requests.RegisterRequest
import com.voicelearn.app.api.responses.ResponseEnglishLearning
import com.voicelearn.app.api.responses.ResponseLogin
import com.voicelearn.app.api.responses.ResponsePronunciationPredict
import com.voicelearn.app.api.responses.ResponseQuizList
import com.voicelearn.app.api.responses.ResponseRegister
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @POST("login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<ResponseLogin>

    @POST("register")
    fun register(
        @Body registerRequest: RegisterRequest
    ): Call<ResponseRegister>

    @GET("spellingListByLevel/{level}")
    fun getSpellingListByLevel(
        @Path("level") level: String
    ): Call<ResponseQuizList>

    @GET("pronunciationListByLevel/{level}")
    fun getPronunciationListByLevel(
        @Path("level") level: String
    ): Call<ResponseQuizList>

    @GET("listArticle")
    fun getArticleList(): Call<ResponseEnglishLearning>

    @Multipart
    @POST("checkPronunciation/{id}")
    fun checkPronunciation(
        @Part file: MultipartBody.Part,
        @Path("id") id: Int
    ): Call<ResponsePronunciationPredict>
}