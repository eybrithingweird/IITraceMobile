package com.example.iitrace

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import java.util.*


class HomeActivity : AppCompatActivity() {
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        val token = SessionManager.getToken(applicationContext)
        if (token.isNullOrBlank()) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
            finish()
        }

        val sharedPreferences = getSharedPreferences("IITrace", Context.MODE_PRIVATE)
        val mode = sharedPreferences.getBoolean("value", false)
        if (mode) { //Night mode = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        val usernameData = SessionManager.getUsernameData(applicationContext)
        val idData = SessionManager.getIdData(applicationContext)
        val emailData = SessionManager.getEmailData(applicationContext)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_adjust)

        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)

        val imageField1 = findViewById<ImageView>(R.id.ivHeader)
        val imageField2 = findViewById<ImageView>(R.id.ivFooter)
        val starter = findViewById<TextView>(R.id.tvLoggedIn)
        val greetingField = findViewById<TextView>(R.id.tvWelcomeTitle)
        val viewBanner = findViewById<View>(R.id.viewBanner)
        val viewBottom = findViewById<View>(R.id.viewBottom)

        val scanQR = findViewById<Button>(R.id.bttnScanQR)
        val qrHistory = findViewById<Button>(R.id.bttnQRhistory)
        val alertHistory = findViewById<Button>(R.id.bttnAlerthistory)
        val reports = findViewById<Button>(R.id.bttnReports)
        val settings = findViewById<Button>(R.id.bttnSettings)
        val signOut = findViewById<Button>(R.id.bttnSignout)
        val survey = findViewById<Button>(R.id.bttnSurvey)

        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        starter.text = "Logged in as $usernameData"

        fun nightModeSet() {
            scanQR.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_red_d)
            qrHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_red_d)
            alertHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_redorange_d)
            reports.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_redorange_d)
            settings.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_orange_d)
            signOut.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_orange_d)
        }

        fun dayModeSet() {
            scanQR.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_red)
            qrHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_red)
            alertHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_redorange)
            reports.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_redorange)
            settings.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_orange)
            signOut.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_orange)
        }

        fun nightModeSetD() {
            scanQR.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_blue_d)
            qrHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_blue_d)
            alertHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_bluepurple_d)
            reports.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_bluepurple_d)
            settings.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_purple_d)
            signOut.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_purple_d)
        }

        fun dayModeSetD() {
            scanQR.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_blue)
            qrHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_blue)
            alertHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_bluepurple)
            reports.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_bluepurple)
            settings.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_purple)
            signOut.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_purple)
        }

        if (timeOfDay < 18) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.red_orange)

            when (this.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> nightModeSet()
                Configuration.UI_MODE_NIGHT_NO -> dayModeSet()
                Configuration.UI_MODE_NIGHT_UNDEFINED -> dayModeSet()
            }
            scanQR.setBackgroundResource(R.drawable.button_square_red)
            qrHistory.setBackgroundResource(R.drawable.button_square_red)
            alertHistory.setBackgroundResource(R.drawable.button_square_redorange)
            reports.setBackgroundResource(R.drawable.button_square_redorange)
            settings.setBackgroundResource(R.drawable.button_square_orange)
            signOut.setBackgroundResource(R.drawable.button_square_orange)

            viewBanner.setBackgroundResource(R.drawable.grad_redyellow)
            viewBottom.setBackgroundResource(R.drawable.grad_redyellow)
//            greetingField.setTextColor(ContextCompat.getColor(this, R.color.near_black))

            imageField1.setImageResource(R.drawable.sunandcloud2)
            imageField2.setImageResource(R.drawable.cloudonly)
            if (timeOfDay < 12) {
                greetingField.text = "Good morning, $usernameData!"
            } else if (timeOfDay == 12) {
                greetingField.text = "Good noon, $usernameData!"
            } else {
                greetingField.text = "Good afternoon, $usernameData!"
            }
        } else if (timeOfDay > 18 || timeOfDay == 18) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.blue_purple)

            when (this.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> nightModeSetD()
                Configuration.UI_MODE_NIGHT_NO -> dayModeSetD()
                Configuration.UI_MODE_NIGHT_UNDEFINED -> dayModeSetD()
            }

            scanQR.setBackgroundResource(R.drawable.button_square_blue)
            qrHistory.setBackgroundResource(R.drawable.button_square_blue)
            alertHistory.setBackgroundResource(R.drawable.button_square_bluepurple)
            reports.setBackgroundResource(R.drawable.button_square_bluepurple)
            settings.setBackgroundResource(R.drawable.button_square_purple)
            signOut.setBackgroundResource(R.drawable.button_square_purple)

            viewBanner.setBackgroundResource(R.drawable.grad_bluepurple)
            viewBottom.setBackgroundResource(R.drawable.grad_bluepurple)
            greetingField.setTextColor(ContextCompat.getColor(this, R.color.lesser_white))

            imageField1.setImageResource(R.drawable.moonandstars3)
            imageField2.setImageResource(R.drawable.starsonly)
            greetingField.text = "Good evening, $usernameData!"
        }

        scanQR.setOnClickListener {
            val intent = Intent(this, ScanQRActivity::class.java)
            startActivity(intent)
            finish()
        }

        qrHistory.setOnClickListener {
            val intent = Intent(this, QRHistoryActivity::class.java)
            startActivity(intent)
            finish()
        }

        alertHistory.setOnClickListener {
            val intent = Intent(this, AlertsActivity::class.java)
            startActivity(intent)
            finish()
        }

        reports.setOnClickListener {
            val intent = Intent(this, ReportsActivity::class.java)
            startActivity(intent)
            finish()
        }

        settings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        signOut.setOnClickListener {
            SessionManager.clearData(applicationContext)
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
            finish()
        }

        survey.setOnClickListener {
            val viewIntent = Intent(
                "android.intent.action.VIEW",
                Uri.parse("http://www.stackoverflow.com/")
            )
            startActivity(viewIntent)
            finish()
        }

    }
}