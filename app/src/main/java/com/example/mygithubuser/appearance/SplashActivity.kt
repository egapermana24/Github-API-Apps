package com.example.mygithubuser.appearance

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mygithubuser.R
import com.example.mygithubuser.datastore.SettingsPreferences
import com.example.mygithubuser.datastore.dataStore
import com.example.mygithubuser.injection.ViewModelSettingsFactory
import com.example.mygithubuser.viewmodel.SettingsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        val splashScreenDuration = 3000L
        val moveMainActivityIntent = Intent(this, MainActivity::class.java)
        lifecycleScope.launch(Dispatchers.Main) {
            delay(splashScreenDuration)
            startActivity(moveMainActivityIntent)
            finish()
        }

        val pref = SettingsPreferences.getInstance(application.dataStore)
        val settingsViewModel = ViewModelProvider(this, ViewModelSettingsFactory(pref))[SettingsViewModel::class.java]
        settingsViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}