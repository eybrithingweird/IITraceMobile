package com.example.iitrace

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import java.util.*


class SettingsActivity : AppCompatActivity() {
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finish()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)

        if (timeOfDay < 18) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.red_orange)
        } else if (timeOfDay > 18 || timeOfDay == 18) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.blue_purple)
        }

        val btn = findViewById<SwitchCompat>(R.id.switchDayNight)
        val statusMode = findViewById<TextView>(R.id.tvStatusMode)
        val chevron = findViewById<ImageButton>(R.id.ibChevron)

        fun nightModeSet() {
            statusMode.text = "Night mode enabled"
            btn.isChecked = true
        }

        fun dayModeSet() {
            statusMode.text = "Night mode disabled"
            btn.isChecked = false
        }

        when (this.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> nightModeSet()
            Configuration.UI_MODE_NIGHT_NO -> dayModeSet()
            Configuration.UI_MODE_NIGHT_UNDEFINED -> dayModeSet()
        }

        val sharedPreferences = getSharedPreferences("IITrace", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        btn.setOnClickListener {
            if (btn.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                statusMode.text = "Night mode enabled"
                chevron.setImageResource(R.drawable.icons8_chevron_w)
                editor.putBoolean("value",true)
                editor.commit()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                statusMode.text = "Night mode disabled"
                chevron.setImageResource(R.drawable.icons8_chevron)
                editor.putBoolean("value",false)
                editor.commit()
            }
        }

        chevron.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}