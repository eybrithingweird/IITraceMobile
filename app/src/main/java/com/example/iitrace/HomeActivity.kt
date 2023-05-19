package com.example.iitrace

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.*


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        val token = SessionManager.getToken(this)
        if (token.isNullOrBlank()) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }

        val usernameData = SessionManager.getUsernameData(this)
        val idData = SessionManager.getIdData(this)
        val emailData = SessionManager.getEmailData(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_day)

        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)

        var imageField1 = findViewById<ImageView>(R.id.ivHeader)
        var imageField2 = findViewById<ImageView>(R.id.ivFooter)
        var greetingField = findViewById<TextView>(R.id.tvWelcomeTitle)
        var viewBanner = findViewById<View>(R.id.viewBanner)
        var viewBottom = findViewById<View>(R.id.viewBottom)

        var scanQR = findViewById<Button>(R.id.bttnScanQR)
        var qrHistory = findViewById<Button>(R.id.bttnQRhistory)
        var alertHistory = findViewById<Button>(R.id.bttnAlerthistory)
        var profile = findViewById<Button>(R.id.bttnProfile)
        var settings = findViewById<Button>(R.id.bttnSettings)
        var signOut = findViewById<Button>(R.id.bttnSignout)

        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        fun nightModeSet() {
            scanQR.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_red_d)
            qrHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_red_d)
            alertHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_redorange_d)
            profile.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_redorange_d)
            settings.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_orange_d)
            signOut.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_orange_d)
        }

        fun dayModeSet() {
            scanQR.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_red)
            qrHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_red)
            alertHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_redorange)
            profile.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_redorange)
            settings.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_orange)
            signOut.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_orange)
        }

        fun nightModeSetD() {
            scanQR.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_blue_d)
            qrHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_blue_d)
            alertHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_bluepurple_d)
            profile.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_bluepurple_d)
            settings.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_purple_d)
            signOut.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_purple_d)
        }

        fun dayModeSetD() {
            scanQR.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_blue)
            qrHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_blue)
            alertHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_bluepurple)
            profile.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_bluepurple)
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
            profile.setBackgroundResource(R.drawable.button_square_redorange)
            settings.setBackgroundResource(R.drawable.button_square_orange)
            signOut.setBackgroundResource(R.drawable.button_square_orange)

            viewBanner.setBackgroundResource(R.drawable.grad_redyellow)
            viewBottom.setBackgroundResource(R.drawable.grad_redyellow)
            greetingField.setTextColor(ContextCompat.getColor(this, R.color.near_black))

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
            profile.setBackgroundResource(R.drawable.button_square_bluepurple)
            settings.setBackgroundResource(R.drawable.button_square_purple)
            signOut.setBackgroundResource(R.drawable.button_square_purple)

            viewBanner.setBackgroundResource(R.drawable.grad_bluepurple)
            viewBottom.setBackgroundResource(R.drawable.grad_bluepurple)
            greetingField.setTextColor(ContextCompat.getColor(this, R.color.lesser_white))

            imageField1.setImageResource(R.drawable.moonandstars3)
            imageField2.setImageResource(R.drawable.starsonly)
            greetingField.text = "Good evening, user!"
        }

        scanQR.setOnClickListener {
            val intent = Intent(this, ScanQRActivity::class.java)
            startActivity(intent)
        }

        qrHistory.setOnClickListener {
            val intent = Intent(this, QRHistoryActivity::class.java)
            startActivity(intent)
        }

        alertHistory.setOnClickListener {
            val intent = Intent(this, AlertsActivity::class.java)
            startActivity(intent)
        }

        settings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        signOut.setOnClickListener {
            SessionManager.clearData(this)
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }

    }
}