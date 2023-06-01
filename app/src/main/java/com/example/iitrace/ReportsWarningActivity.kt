package com.example.iitrace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.Calendar


class ReportsWarningActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reports_warning)

        val bttn = findViewById<Button>(R.id.bttnReportContinue)
        val checkBox = findViewById<CheckBox>(R.id.cbAgree)
        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)

        if (timeOfDay < 18) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.red_orange)
        } else if (timeOfDay >= 18 || timeOfDay == 18) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.blue_purple)
        }

        bttn.setBackgroundResource(R.drawable.button_theme_3)

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            bttn.isEnabled = isChecked
            if (checkBox.isChecked) {
                bttn.setBackgroundResource(R.drawable.button_theme_2)
            } else {
                bttn.setBackgroundResource(R.drawable.button_theme_3)
            }
        }

        bttn.setOnClickListener {
            val intent = Intent(this, ReportsCreateActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}