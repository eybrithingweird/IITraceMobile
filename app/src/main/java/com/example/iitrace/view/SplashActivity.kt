package com.example.iitrace.view

import com.example.iitrace.MainActivity
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.iitrace.R
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //hide status bar
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val sharedPreferences = getSharedPreferences("IITrace", Context.MODE_PRIVATE)
        val mode = sharedPreferences.getBoolean("value", false)
        val appname = findViewById<TextView>(R.id.appname)
        if (mode) { //Night mode = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            appname.setTextColor(ContextCompat.getColor(this, R.color.white))
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            appname.setTextColor(ContextCompat.getColor(this, R.color.near_black))
        }

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            },
            2000
        )
    }
}