package com.example.iitrace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import java.util.*

class AlertsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alert_history)

        val loadingBar = findViewById<ProgressBar>(R.id.pbAlerts)
        val chevron = findViewById<ImageButton>(R.id.ibChevron)
        val chevron_small = findViewById<ImageView>(R.id.ivSmallChevron)
        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)

        if (timeOfDay < 18) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.red_orange)
        } else if (timeOfDay > 18 || timeOfDay == 18) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.blue_purple)
        }

        fun nightModeSet() {
            chevron.setImageResource(R.drawable.icons8_chevron_w)
            chevron_small.setImageResource(R.drawable.icons8_chevron_small_w)
        }

        fun dayModeSet() {
            chevron.setImageResource(R.drawable.icons8_chevron)
            chevron_small.setImageResource(R.drawable.icons8_chevron_small)
        }

        when (this.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> nightModeSet()
            Configuration.UI_MODE_NIGHT_NO -> dayModeSet()
            Configuration.UI_MODE_NIGHT_UNDEFINED -> dayModeSet()
        }

        chevron.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }
}