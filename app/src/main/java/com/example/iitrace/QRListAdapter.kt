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

        val dayOfTheWeek: String
        val day: String
        val monthString: String
        val monthNumber: String
        val year: String

        val timeEntry: String = format("HH:mm:ss", history.date_entered) as String
        val timeExit: String = format("HH:mm:ss", history.date_exited) as String

        if (timeEntry == "null") {
            dayOfTheWeek = format("EEE", history.date_exited) as String // Thursday
            day = format("dd", history.date_exited) as String // 25
            monthString = format("MMM", history.date_exited) as String // May
            monthNumber = format("MM", history.date_exited) as String // 05
            year = format("yyyy", history.date_exited) as String // 2023

            dateview.text = "$dayOfTheWeek, $monthString $day"
            yearview.text = year
            bldgview.text = history.building_name
            roomview.text = history.room_name
            timeEntryView.text = "Entry time: $timeEntry"
            timeExitView.text = "Exit time: $timeExit"
        } else {
            dayOfTheWeek = format("EEE", history.date_entered) as String // Thursday
            day = format("dd", history.date_entered) as String // 25
            monthString = format("MMM", history.date_entered) as String // May
            monthNumber = format("MM", history.date_entered) as String // 05
            year = format("yyyy", history.date_entered) as String // 2023

            dateview.text = "$dayOfTheWeek, $monthString $day"
            yearview.text = year
            bldgview.text = history.building_name
            roomview.text = history.room_name
            timeEntryView.text = "Entry time: $timeEntry"
            timeExitView.text = "Exit time: $timeExit"
        }
    }

    override fun getItemCount(): Int {
        return mHistory.size
    }

    // Returns the total count of items in the list
//    override fun getItemCount(): Int {
//        return mHistory?.size
//    }
}