package com.example.iitrace

//import com.example.iitrace.databinding.SignInBinding

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.iitrace.network.data.requests.LoginRequest
import com.example.iitrace.viewmodel.IITraceViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.security.AccessController.getContext
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val iitraceViewModel: IITraceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        val token = SessionManager.getToken(applicationContext)
        if (!token.isNullOrBlank()) {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
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
            window.statusBarColor = ContextCompat.getColor(this, R.color.blue_purple)
            faderView.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_purple))
        }

        val username = findViewById<EditText>(R.id.etUsername)
        val password = findViewById<EditText>(R.id.etPassword)

        username.background = ContextCompat.getDrawable(this, R.drawable.text_input_dark)
        password.background = ContextCompat.getDrawable(this, R.drawable.text_input_dark)

        val continueClick = findViewById<Button>(R.id.bttnContinue)
        continueClick.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()

            if (user.isEmpty()){
                Toast.makeText(this@MainActivity, "Username required", Toast.LENGTH_LONG).show()
            } else if (pass.isEmpty()){
                Toast.makeText(this@MainActivity, "Password required", Toast.LENGTH_LONG).show()
            } else {
                val loginRequest = LoginRequest(user, pass)
                iitraceViewModel.login(loginRequest)
                observeLogin()
            }
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
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

                    Toast.makeText(this@MainActivity, "Login successful!", Toast.LENGTH_LONG).show()
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
                    }

                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                    loadingBar.visibility = View.INVISIBLE
                    fader.visibility = View.INVISIBLE
                    continueButton.visibility = View.VISIBLE
                    myiitButton.visibility = View.VISIBLE
                    getWindow().clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE )
                    Toast.makeText(this@MainActivity, "Login Failure: ${data.error}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}