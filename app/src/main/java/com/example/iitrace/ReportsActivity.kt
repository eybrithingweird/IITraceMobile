package com.example.iitrace

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.contacts.QRListAdapter
import com.example.contacts.ReportsListAdapter
import com.example.iitrace.network.data.responses.HistoryResponse
import com.example.iitrace.network.data.responses.ReportsResponse
import com.example.iitrace.viewmodel.IITraceViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ReportsActivity : AppCompatActivity() {
    private val iitraceViewModel: IITraceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reports)

        val token = SessionManager.getToken(applicationContext)

        fun getHeaderMap(): Map<String, String> {
            return mapOf("Authorization" to "Token $token")
        }

        iitraceViewModel.reports(getHeaderMap())
        observeReports()

        val chevron = findViewById<ImageButton>(R.id.ibChevron)
        val bttn = findViewById<Button>(R.id.bttnCreateReport)
        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)

        if (timeOfDay < 18) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.red_orange)
        } else if (timeOfDay > 18 || timeOfDay == 18) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.blue_purple)
        }

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

        val pullToRefresh = findViewById<SwipeRefreshLayout>(R.id.viewCenter)
        pullToRefresh.setOnRefreshListener {
            iitraceViewModel.history(getHeaderMap())
            observeReports()
            pullToRefresh.isRefreshing = false
        }

        chevron.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        bttn.setOnClickListener {
            val intent = Intent(this, ReportsWarningActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun observeReports() {
        iitraceViewModel._reportsState.observe(this) { data ->
            val loadingBar = findViewById<ProgressBar>(R.id.pbReports)
            val textViewNull = findViewById<TextView>(R.id.tvNull)
            when {
                data.isLoading -> {
                    loadingBar.visibility = View.VISIBLE
                }
                data.data != null -> {
                    loadingBar.visibility = View.INVISIBLE

                    Toast.makeText(this@ReportsActivity, "Processing successful!", Toast.LENGTH_LONG).show()
                    if (!data?.data?.isEmpty()!!) {
                        val rvReports = findViewById<View>(R.id.recycler_view) as RecyclerView
                        val reportsArr: ArrayList<ReportsResponse> = data.data!!
                        val adapter = ReportsListAdapter(reportsArr)
                        rvReports.adapter = adapter
                        rvReports.layoutManager = LinearLayoutManager(this)
                        (rvReports.layoutManager as LinearLayoutManager).reverseLayout = true
                        (rvReports.layoutManager as LinearLayoutManager).stackFromEnd = true
                        rvReports.setHasFixedSize(true)
                        rvReports.scrollToPosition(0)
                    } else {
                        textViewNull.visibility = View.VISIBLE
                    }
                }
                else -> {
                    loadingBar.visibility = View.INVISIBLE
//                    Toast.makeText(this@ReportsActivity, "Failure: ${data.error}", Toast.LENGTH_LONG).show()
                    textViewNull.visibility = View.VISIBLE
                    textViewNull.text = "Failure: ${data.error}"
                }
            }
        }
    }
}