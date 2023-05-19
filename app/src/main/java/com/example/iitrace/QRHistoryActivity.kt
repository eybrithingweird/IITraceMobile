package com.example.iitrace

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.*

class QRHistoryActivity  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qr_history)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.viewCenter, QRListFragment.newInstance(), "qrList")
                .commit()
        }

        val loadingBar = findViewById<ProgressBar>(R.id.pbHistory)
        val chevron = findViewById<ImageButton>(R.id.ibChevron)
        val chevron_small = findViewById<ImageView>(R.id.ivSmallChevron)
        val field = findViewById<ImageView>(R.id.ivField1)
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
            field.setImageResource(R.drawable.field_withbottom_dark)
        }

        fun dayModeSet() {
            chevron.setImageResource(R.drawable.icons8_chevron)
            chevron_small.setImageResource(R.drawable.icons8_chevron_small)
            field.setImageResource(R.drawable.field_withbottom_light)
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