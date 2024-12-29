package com.voicelearn.app.ui.splash

import androidx.lifecycle.ViewModel
import com.voicelearn.app.data.Repository

class SplashViewModel(private val repository: Repository) : ViewModel() {

    fun getSignedInUserName(): String? {
        return repository.getSignedInUserName()
    }
}