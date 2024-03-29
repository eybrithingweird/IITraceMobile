package com.example.iitrace

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.iitrace.network.data.requests.LoginRequest
import com.example.iitrace.viewmodel.IITraceViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val iitraceViewModel: IITraceViewModel by viewModels()
    private var firebaseToken: String = ""

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
            PackageManager.PERMISSION_DENIED ) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        val token = SessionManager.getToken(applicationContext)
        if (!token.isNullOrBlank()) {
            finish()
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }

        val sharedPreferences = getSharedPreferences("IITrace", Context.MODE_PRIVATE)
        val mode = sharedPreferences.getBoolean("value", false)
        if (mode) { //Night mode = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)
        val faderView = findViewById<View>(R.id.viewFader)

        if (timeOfDay < 18) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.red_orange)
            faderView.setBackgroundColor(ContextCompat.getColor(this, R.color.red_orange))
        } else if (timeOfDay > 18 || timeOfDay == 18) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.grad_yellow)
            faderView.setBackgroundColor(ContextCompat.getColor(this, R.color.grad_yellow))
        }

        val username = findViewById<EditText>(R.id.etUsername)
        val password = findViewById<EditText>(R.id.etPassword)

        username.background = ContextCompat.getDrawable(this, R.drawable.text_input_dark)
        password.background = ContextCompat.getDrawable(this, R.drawable.text_input_dark)

        val continueClick = findViewById<Button>(R.id.bttnContinue)
        val iitClick = findViewById<Button>(R.id.bttnMyIIT)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("Firebase", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            firebaseToken = task.result
        })

        continueClick.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()

            if (user.isEmpty()){
                Toast.makeText(this@MainActivity, "Username required", Toast.LENGTH_LONG).show()
            } else if (pass.isEmpty()){
                Toast.makeText(this@MainActivity, "Password required", Toast.LENGTH_LONG).show()
            } else {
                val loginRequest = LoginRequest(user, pass, firebaseToken)
                iitraceViewModel.login(loginRequest)
                observeLogin()
            }
        }

        iitClick.setOnClickListener {
            Toast.makeText(this@MainActivity, "Feature not yet available!", Toast.LENGTH_LONG).show()
        }

    }

//    fun runtimeEnableAutoInit() {
//        // [START fcm_runtime_enable_auto_init]
//        Firebase.messaging.isAutoInitEnabled = true
//        // [END fcm_runtime_enable_auto_init]
//    }

    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            askNotificationPermission()
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun observeLogin() {
        iitraceViewModel._loginState.observe(this) { data ->
            val loadingBar = findViewById<ProgressBar>(R.id.pbLogin)
            val fader = findViewById<View>(R.id.viewFader)
            val continueButton = findViewById<Button>(R.id.bttnContinue)
            val myiitButton = findViewById<Button>(R.id.bttnMyIIT)
            when {
                data.isLoading -> {
                    getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    loadingBar.visibility = View.VISIBLE
                    fader.visibility = View.VISIBLE
                    continueButton.visibility = View.INVISIBLE
                    myiitButton.visibility = View.INVISIBLE
                }
                data.data != null -> {
                    loadingBar.visibility = View.INVISIBLE
                    fader.visibility = View.INVISIBLE
                    continueButton.visibility = View.VISIBLE
                    myiitButton.visibility = View.VISIBLE
                    getWindow().clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE )

//                    Toast.makeText(this@MainActivity, "Login successful!", Toast.LENGTH_LONG).show()
                    if (!data?.data?.token.isNullOrEmpty()) {
                        data?.data?.token?.let {
                            SessionManager.saveAuthToken(applicationContext, it)
                        }

                        data?.data?.user_data?.id?.let {
                            SessionManager.saveUserData(applicationContext, "id", it)
                        }

                        data?.data?.user_data?.username?.let {
                            SessionManager.saveUserData(applicationContext, "username", it)
                        }

                        data?.data?.user_data?.email?.let {
                            SessionManager.saveUserData(applicationContext, "email", it)
                        }

                        data?.data?.expiry?.let {
                            SessionManager.saveUserData(applicationContext, "expiry", it)
                        }
                    }

                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> {
                    loadingBar.visibility = View.INVISIBLE
                    fader.visibility = View.INVISIBLE
                    continueButton.visibility = View.VISIBLE
                    myiitButton.visibility = View.VISIBLE
                    getWindow().clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE )
                    Toast.makeText(this@MainActivity, "Login Failure: ${data.error}.\nCheck your credentials and/or internet connection, and try again.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}