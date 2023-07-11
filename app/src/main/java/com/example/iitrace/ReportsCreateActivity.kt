package com.example.iitrace

import android.R
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.iitrace.network.data.requests.CreateRepRequest
import com.example.iitrace.network.data.requests.LoginRequest
import com.example.iitrace.network.data.responses.DiseasesResponse
import com.example.iitrace.viewmodel.IITraceViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar


@AndroidEntryPoint
class ReportsCreateActivity : AppCompatActivity() {
    private val iitraceViewModel: IITraceViewModel by viewModels()
    var idArray = arrayListOf<Int>()
    var idChosen: Int = 0

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(com.example.iitrace.R.layout.reports_create)

        val token = SessionManager.getToken(applicationContext)

        fun getHeaderMap(): Map<String, String> {
            return mapOf("Authorization" to "Token $token")
        }

        iitraceViewModel.diseases(getHeaderMap())
        observeDiseases()

        val bttn = findViewById<Button>(com.example.iitrace.R.id.bttnReportCreate)
        val spinnerView = findViewById<Spinner>(com.example.iitrace.R.id.spinnerDrpdwn)
        val chevron = findViewById<ImageButton>(com.example.iitrace.R.id.ibChevron)
        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)

        if (timeOfDay < 18) {
            window.statusBarColor = ContextCompat.getColor(this, com.example.iitrace.R.color.red_orange)
        } else if (timeOfDay > 18 || timeOfDay == 18) {
            window.statusBarColor = ContextCompat.getColor(this, com.example.iitrace.R.color.blue_purple)
        }

        fun nightModeSet() {
            chevron.setImageResource(com.example.iitrace.R.drawable.icons8_chevron_w)
        }

        fun dayModeSet() {
            chevron.setImageResource(com.example.iitrace.R.drawable.icons8_chevron)
        }

        when (this.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> nightModeSet()
            Configuration.UI_MODE_NIGHT_NO -> dayModeSet()
            Configuration.UI_MODE_NIGHT_UNDEFINED -> dayModeSet()
        }

        bttn.setBackgroundResource(com.example.iitrace.R.drawable.button_theme_3)

        spinnerView.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                if(selectedItem.equals("--CHOOSE A DISEASE--")) {
                    bttn.isEnabled = false
                    bttn.setBackgroundResource(com.example.iitrace.R.drawable.button_theme_3)
                } else {
                    bttn.isEnabled = true
                    bttn.setBackgroundResource(com.example.iitrace.R.drawable.button_theme_2)
                    idChosen = idArray[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        bttn.setOnClickListener {
            val createrepRequest = CreateRepRequest(idChosen)
            iitraceViewModel.createrep(getHeaderMap(), createrepRequest)
            observeCreateRep()
        }

        chevron.setOnClickListener {
            val intent = Intent(this, ReportsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun observeDiseases() {
        iitraceViewModel._diseasesState.observe(this) { data ->
            val loadingBar = findViewById<ProgressBar>(com.example.iitrace.R.id.pbCreateReport)
            val textViewNull = findViewById<TextView>(com.example.iitrace.R.id.tvNull)
            val bttn = findViewById<Button>(com.example.iitrace.R.id.bttnReportCreate)
            when {
                data.isLoading -> {
                    loadingBar.visibility = View.VISIBLE
                }
                data.data != null -> {
                    loadingBar.visibility = View.INVISIBLE

//                    Toast.makeText(this@ReportsCreateActivity, "Processing successful!", Toast.LENGTH_LONG).show()
                    val spinnerView = findViewById<Spinner>(com.example.iitrace.R.id.spinnerDrpdwn)
                    val arrayList = data.data!!.sortedBy { it.name }
//                    textViewNull.visibility = View.VISIBLE
//                    textViewNull.text = arrayList[0].toString()
                    val arrayAdapter: ArrayAdapter<String> =
                        ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
                    arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

                    arrayAdapter.add("--CHOOSE A DISEASE--")
                    idArray.add(0)

                    for (i in arrayList.indices) {
                        arrayAdapter.add(arrayList[i].name)
                        idArray.add(arrayList[i].id)
                        arrayAdapter.notifyDataSetChanged()
                    }

                    spinnerView.setAdapter(arrayAdapter)
                }
                else -> {
                    loadingBar.visibility = View.INVISIBLE
                    bttn.visibility = View.INVISIBLE
                    textViewNull.visibility = View.VISIBLE
                    textViewNull.text = "Failure: ${data.error}"
                }
            }
        }
    }

    private fun observeCreateRep() {
        iitraceViewModel._createrepState.observe(this) { data ->
            val loadingBar = findViewById<ProgressBar>(com.example.iitrace.R.id.pbCreateReport)
            val textViewNull = findViewById<TextView>(com.example.iitrace.R.id.tvNull)
            val bttn = findViewById<Button>(com.example.iitrace.R.id.bttnReportCreate)
            val fader = findViewById<View>(com.example.iitrace.R.id.viewFader)
            when {
                data.isLoading -> {
                    loadingBar.visibility = View.VISIBLE
                    getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    fader.visibility = View.VISIBLE
                    bttn.visibility = View.INVISIBLE
                }
                data.data != null -> {
                    loadingBar.visibility = View.INVISIBLE
                    getWindow().clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE )
                    fader.visibility = View.INVISIBLE
                    bttn.visibility = View.VISIBLE

//                    Toast.makeText(this@ReportsCreateActivity, "Processing successful!", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, ReportsActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> {
                    loadingBar.visibility = View.INVISIBLE
                    bttn.visibility = View.INVISIBLE
                    textViewNull.visibility = View.VISIBLE
                    textViewNull.text = "Failure: ${data.error}"
                }
            }
        }
    }
}