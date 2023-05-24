package com.example.iitrace

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.QRListAdapter
import com.example.iitrace.network.data.responses.HistoryResponse
import com.example.iitrace.viewmodel.IITraceViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class QRHistoryActivity  : AppCompatActivity() {
    private val iitraceViewModel: IITraceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        val token = SessionManager.getToken(applicationContext)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.qr_history)

        fun getHeaderMap(): Map<String, String> {
            return mapOf("Authorization" to "Token $token")
        }

//        val historyRequest = HistoryRequest(token)
        iitraceViewModel.history(getHeaderMap())
        observeHistory()

//        val loadingBar = findViewById<ProgressBar>(R.id.pbHistory)
        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)
        val chevron = findViewById<ImageButton>(R.id.ibChevron)

        fun nightModeSet() {
            chevron.setImageResource(R.drawable.icons8_chevron_w)
        }

        fun dayModeSet() {
            chevron.setImageResource(R.drawable.icons8_chevron)
        }

        when (this.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> nightModeSet()
            Configuration.UI_MODE_NIGHT_NO -> dayModeSet()
            Configuration.UI_MODE_NIGHT_UNDEFINED -> dayModeSet()
        }

        if (timeOfDay < 18) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.red_orange)
        } else if (timeOfDay > 18 || timeOfDay == 18) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.blue_purple)
        }

        chevron.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeHistory() {
        iitraceViewModel._historyState.observe(this) { data ->
            val loadingBar = findViewById<ProgressBar>(R.id.pbHistory)
            when {
                data.isLoading -> {
                    loadingBar.visibility = View.VISIBLE
                }
                data.data != null -> {
                    loadingBar.visibility = View.INVISIBLE

                    Toast.makeText(this@QRHistoryActivity, "Processing successful!", Toast.LENGTH_LONG).show()
                    if (!data?.data?.isNullOrEmpty()!!) {
                        val rvHistory = findViewById<View>(R.id.recycler_view) as RecyclerView
                        var historyArr: ArrayList<HistoryResponse> = data?.data!!
                        val adapter = QRListAdapter(historyArr)
                        rvHistory.adapter = adapter
                        rvHistory.layoutManager = LinearLayoutManager(this)
                        (rvHistory.layoutManager as LinearLayoutManager).reverseLayout = true
                        (rvHistory.layoutManager as LinearLayoutManager).stackFromEnd = true
                        rvHistory.setHasFixedSize(true)
                        rvHistory.scrollToPosition(0)
                    }
                }
                else -> {
                    loadingBar.visibility = View.INVISIBLE
                    Toast.makeText(this@QRHistoryActivity, "Failure: ${data.error}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}