package com.example.iitrace

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.contacts.AlertsListAdapter
import com.example.iitrace.network.data.responses.AlertsResponse
import com.example.iitrace.viewmodel.IITraceViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AlertsActivity : AppCompatActivity() {
    private val iitraceViewModel: IITraceViewModel by viewModels()
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finish()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        val token = SessionManager.getToken(applicationContext)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.alert_history)

        fun getHeaderMap(): Map<String, String> {
            return mapOf("Authorization" to "Token $token")
        }

        iitraceViewModel.alerts(getHeaderMap())
        observeAlerts()

        val chevron = findViewById<ImageButton>(R.id.ibChevron)
        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)

        if (timeOfDay < 18) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.red_orange)
        } else {
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
            val textViewNull = findViewById<TextView>(R.id.tvNull)
            textViewNull.visibility = View.INVISIBLE
            iitraceViewModel.alerts(getHeaderMap())
            observeAlerts()
            pullToRefresh.isRefreshing = false
        }

        chevron.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun observeAlerts() {
        iitraceViewModel._alertsState.observe(this) { data ->
            val loadingBar = findViewById<ProgressBar>(R.id.pbAlerts)
            val textViewNull = findViewById<TextView>(R.id.tvNull)
            when {
                data.isLoading -> {
                    loadingBar.visibility = View.VISIBLE
                }
                data.data != null -> {
                    loadingBar.visibility = View.INVISIBLE

//                    Toast.makeText(this@AlertsActivity, "Processing successful!", Toast.LENGTH_LONG).show()
                    if (!data?.data?.isEmpty()!!) {
                        val rvAlerts = findViewById<View>(R.id.recycler_view) as RecyclerView
                        val alertsArr: ArrayList<AlertsResponse> = data.data!!
                        val adapter = AlertsListAdapter(alertsArr)
                        rvAlerts.adapter = adapter
                        rvAlerts.layoutManager = LinearLayoutManager(this)
                        (rvAlerts.layoutManager as LinearLayoutManager).reverseLayout = true
                        (rvAlerts.layoutManager as LinearLayoutManager).stackFromEnd = true
                        rvAlerts.setHasFixedSize(true)
                        rvAlerts.scrollToPosition(-1)
                    } else {
                        textViewNull.visibility = View.VISIBLE
                    }
                }
                else -> {
                    loadingBar.visibility = View.INVISIBLE
//                    Toast.makeText(this@QRHistoryActivity, "Failure: ${data.error}", Toast.LENGTH_LONG).show()
                    textViewNull.visibility = View.VISIBLE
                    textViewNull.text = "Failure: ${data.error}"
                }
            }
        }
    }
}