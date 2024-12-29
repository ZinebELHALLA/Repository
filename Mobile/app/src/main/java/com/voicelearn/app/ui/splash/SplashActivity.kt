package com.voicelearn.app.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.voicelearn.app.databinding.ActivitySplashBinding
import com.voicelearn.app.helper.ViewModelFactory
import com.voicelearn.app.ui.login.LoginActivity
import com.voicelearn.app.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    private val viewModel: SplashViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed({
            val userName = viewModel.getSignedInUserName()

            // Check Login
            if (userName.isNullOrEmpty()) {
                Intent(this, LoginActivity::class.java).also {
                    startActivity(it)
                }
            } else {
                Intent(this, MainActivity::class.java).also {
                    it.putExtra("userName", userName)
                    startActivity(it)
                }
            }
            finish()
        }, SECONDS_IN_MILLISECONDS)
    }

    companion object {
        private const val SECONDS_IN_MILLISECONDS: Long = 3000
    }
}