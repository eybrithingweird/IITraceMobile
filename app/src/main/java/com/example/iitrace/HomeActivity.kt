package com.example.iitrace

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : AppCompatActivity() {
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        startActivity(intent)
        finish()
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        val token = SessionManager.getToken(applicationContext)
        if (token.isNullOrBlank()) {
            finish()
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }

        val expiry = SessionManager.getExpiryData(applicationContext)!!
        val currentTime = Calendar.getInstance().time

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val dateFormat = sdf.parse(expiry)
        val tempFormat = sdf.format(currentTime)
        val calFormat = sdf.parse(tempFormat)

        val inputPattern = "yyyy-MM-dd HH:mm:ss"
        val inputFormat = SimpleDateFormat(inputPattern)
        val expiryFormat = dateFormat?.let { inputFormat.format(it) }
        val currentFormat = calFormat?.let { inputFormat.format(it) }

//        Log.d("Expiry", expiryFormat.toString())
//        Log.d("Test", currentFormat.toString())
//        Log.d("Tester", (expiryFormat.toString() < currentFormat.toString()).toString())
        if (expiryFormat.toString() < currentFormat.toString()){
            SessionManager.clearData(applicationContext)
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
            finish()
        } else {
            Log.d("Helper", "Still logged in")
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
            scanQR.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_redorange_d)
            qrHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_redorange_d)
            alertHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_redorange_d)
            reports.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_orange_d)
            settings.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_orange_d)
            signOut.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_orange_d)
        }

        fun dayModeSet() {
            scanQR.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_redorange)
            qrHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_redorange)
            alertHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_redorange)
            reports.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_orange)
            settings.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_orange)
            signOut.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_orange)
        }

        fun nightModeSetD() {
            scanQR.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_bluepurple_d)
            qrHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_bluepurple_d)
            alertHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_bluepurple_d)
            reports.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_purple_d)
            settings.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_purple_d)
            signOut.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_purple_d)
        }

        fun dayModeSetD() {
            scanQR.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_bluepurple)
            qrHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_bluepurple)
            alertHistory.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_bluepurple)
            reports.backgroundTintList = ContextCompat.getColorStateList(this, R.color.square_purple)
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

            imageField1.setImageResource(R.drawable.sunandcloud2)
            imageField2.setImageResource(R.drawable.cloudonly)

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

            imageField1.setImageResource(R.drawable.moonandstars3)
            imageField2.setImageResource(R.drawable.starsonly)
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

        reports.setOnClickListener {
            val intent = Intent(this, ReportsActivity::class.java)
            startActivity(intent)
        }

        settings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
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
                Uri.parse("https://forms.gle/z7SdVzw9g538WDGH9")
            )
            startActivity(viewIntent)
            finish()
        }

    }
}