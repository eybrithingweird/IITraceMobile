package com.example.contacts

//import java.text.DateFormat
import android.annotation.SuppressLint
import android.content.res.Configuration
import android.text.format.DateFormat.format
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.iitrace.PopUpClass
import com.example.iitrace.R
import com.example.iitrace.network.data.responses.AlertsResponse
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class AlertsListAdapter (
    private val mAlerts: ArrayList<AlertsResponse>
) : RecyclerView.Adapter<AlertsListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textListDate = itemView.findViewById<TextView>(R.id.tvListTitle)
        val textListMessage = itemView.findViewById<TextView>(R.id.tvListMessage)
        val button = itemView.findViewById<MaterialButton>(R.id.ivSmallChevron)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        when (context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                val alertsView = inflater.inflate(R.layout.row_alerts_dark, parent, false)
                return ViewHolder(alertsView)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                val alertsView = inflater.inflate(R.layout.row_alerts, parent, false)
                return ViewHolder(alertsView)
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                val alertsView = inflater.inflate(R.layout.row_alerts, parent, false)
                return ViewHolder(alertsView)
            }
        }
        val alertsView = inflater.inflate(R.layout.row_alerts, parent, false)
        return ViewHolder(alertsView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: AlertsListAdapter.ViewHolder, position: Int) {
        val alerts: AlertsResponse = mAlerts.get(position)
        val dateview = viewHolder.textListDate
        val msgview = viewHolder.textListMessage
        val buttonPop = viewHolder.button

        val dateRes: String = alerts.date_created

        val locale = Locale("en", "PH")
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS", locale)
        sdf.timeZone = TimeZone.getTimeZone("PHT")
        val dateTest = sdf.parse(dateRes)

        val dayOfTheWeek: String = format("EEE", dateTest) as String // Thursday
        val day: String = format("dd", dateTest) as String // 25
        val monthString: String = format("MMM", dateTest) as String // May
//        val monthNumber: String = format("MM", dateTest) as String // 05
        val year: String = format("yyyy", dateTest) as String // 2023
        val timeText: String = format("HH:mm:ss", dateTest) as String //04:05:23

        val stringMsg = alerts.alert_message
        val arr: List<String> = stringMsg.split("\\s".toRegex())

        val N = 4 // NUMBER OF WORDS THAT YOU NEED
        var nWords = ""

        // concatenating number of words that you required
        if (arr.size > 4){
            for (i in 0 until N) {
                nWords = nWords + " " + arr[i]
                msgview.text = "$nWords...."
            }
        } else {
            nWords = stringMsg
            msgview.text = "$nWords"
        }

        dateview.text = "$dayOfTheWeek $monthString $day, $year, $timeText"
        val titleMsg = "$dayOfTheWeek $monthString $day, $year, $timeText"

        buttonPop.setOnClickListener { v ->
            val popUpClass = PopUpClass()
            popUpClass.showPopupWindow(v, titleMsg, stringMsg)
        }
    }

    override fun getItemCount(): Int {
        return mAlerts.size
    }

}