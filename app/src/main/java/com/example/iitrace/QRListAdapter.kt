package com.example.contacts

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.text.format.DateFormat.format
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.iitrace.R
import com.example.iitrace.network.data.responses.HistoryResponse
//import java.text.DateFormat
import android.text.format.DateFormat
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class QRListAdapter (
    private val mHistory: ArrayList<HistoryResponse>
    ) : RecyclerView.Adapter<QRListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textListDate = itemView.findViewById<TextView>(R.id.tvListDate)
        val textListYear = itemView.findViewById<TextView>(R.id.tvListTime)
        val textListBldg = itemView.findViewById<TextView>(R.id.tvListBuilding)
        val textListRoom = itemView.findViewById<TextView>(R.id.tvListRoom)
        val textListTimeEntry = itemView.findViewById<TextView>(R.id.tvListTimeEntered)
        val textListTimeExit = itemView.findViewById<TextView>(R.id.tvListTimeExited)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        when (context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                val historyView = inflater.inflate(R.layout.row_qrhistory_dark, parent, false)
                return ViewHolder(historyView)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                val historyView = inflater.inflate(R.layout.row_qrhistory, parent, false)
                return ViewHolder(historyView)
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                val historyView = inflater.inflate(R.layout.row_qrhistory, parent, false)
                return ViewHolder(historyView)
            }
        }
        val historyView = inflater.inflate(R.layout.row_qrhistory, parent, false)
        return ViewHolder(historyView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: QRListAdapter.ViewHolder, position: Int) {
        val history: HistoryResponse = mHistory.get(position)
        val dateview = viewHolder.textListDate
        val yearview = viewHolder.textListYear
        val bldgview = viewHolder.textListBldg
        val roomview = viewHolder.textListRoom
        val timeEntryView = viewHolder.textListTimeEntry
        val timeExitView = viewHolder.textListTimeExit

        var timeExit: String
        val timeEntry: String

        if (history.date_exited == null){
            timeExit = "null"
        } else {
//            val temp = sdf.format(history.date_exited)
//            timeExit = temp.toString()
//            val dateTimeChange = Date(history.date_exited - 28800 * 1000)
//            Log.d("check", history.date_exited.toString())
            var hour = Integer.parseInt(format("HH", history.date_exited) as String)
            if (hour < 12) {
                hour += 16
            } else {
                hour -= 8
            }
            val minutes = Integer.parseInt(format("mm", history.date_exited) as String)
            val seconds = Integer.parseInt(format("ss", history.date_exited) as String)

            val hoursTxt: String
            val minutesTxt: String
            val secondsTxt: String

            if (hour < 10){
                hoursTxt = "0$hour"
            } else {
                hoursTxt = hour.toString()
            }

            if (minutes < 10){
                minutesTxt = "0$minutes"
            } else {
                minutesTxt = minutes.toString()
            }

            if (seconds == 0){
                secondsTxt = "00"
            } else if (seconds < 10) {
                secondsTxt = "0$seconds"
            } else {
                secondsTxt = seconds.toString()
            }

            timeExit = "$hoursTxt:$minutesTxt:$secondsTxt"

//            timeExit = history.date_exited.toString()

//            timeExit = format("HH:mm:ss", history.date_exited) as String
        }

        if (history.date_created == null){
            timeEntry = "null"
        } else {
//            val temp = sdf.format(history.date_created)
//            timeEntry = temp.toString()
            timeEntry = format("HH:mm:ss", history.date_created) as String
//            timeEntry = history.date_created.toString()
        }

        val dayOfTheWeek: String = format("EEE", history.date_created) as String // Thursday
        val day: String = format("dd", history.date_created) as String // 25
        val monthString: String = format("MMM", history.date_created) as String // May
        val monthNumber: String = format("MM", history.date_created) as String // 05
        val year: String = format("yyyy", history.date_created) as String // 2023

        dateview.text = "$dayOfTheWeek, $monthString $day"
        yearview.text = year
        bldgview.text = history.building_name
        roomview.text = history.room_name
        timeEntryView.text = "Entry time: $timeEntry"
        timeExitView.text = "Exit time: $timeExit"
    }

    override fun getItemCount(): Int {
        return mHistory.size
    }

    // Returns the total count of items in the list
//    override fun getItemCount(): Int {
//        return mHistory?.size
//    }
}