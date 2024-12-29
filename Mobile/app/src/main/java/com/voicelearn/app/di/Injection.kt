package com.voicelearn.app.di

import android.content.Context
import com.voicelearn.app.api.ApiConfig
import com.voicelearn.app.data.Repository

object Injection {

    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        val sharedPreferences =
            context.getSharedPreferences("liguity_shared_pref", Context.MODE_PRIVATE)

        return Repository.getInstance(apiService, sharedPreferences)
    }
}