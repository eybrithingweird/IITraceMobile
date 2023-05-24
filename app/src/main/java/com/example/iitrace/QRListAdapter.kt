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

        val field = itemView.findViewById<ImageView>(R.id.ivField1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        when (context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                var historyView = inflater.inflate(R.layout.row_qrhistory_dark, parent, false)
                return ViewHolder(historyView)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                var historyView = inflater.inflate(R.layout.row_qrhistory, parent, false)
                return ViewHolder(historyView)
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                var historyView = inflater.inflate(R.layout.row_qrhistory, parent, false)
                return ViewHolder(historyView)
            }
        }
        var historyView = inflater.inflate(R.layout.row_qrhistory, parent, false)
        return ViewHolder(historyView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: QRListAdapter.ViewHolder, position: Int) {
        val history: HistoryResponse? = mHistory?.get(position)
        val dateview = viewHolder.textListDate
        val yearview = viewHolder.textListYear
        val bldgview = viewHolder.textListBldg
        val roomview = viewHolder.textListRoom
        val timeEntryView = viewHolder.textListTimeEntry
        val timeExitView = viewHolder.textListTimeExit

//        WARNING! MAKE SURE TO GET DATE_ENTERED INSTEAD OF DATE_CREATED DATA

        val dayOfTheWeek = format("EEE", history?.date_created) as String // Thursday
        val day = format("dd", history?.date_created) as String // 20
        val monthString = format("MMM", history?.date_created) as String // Jun
        val monthNumber = format("MM", history?.date_created) as String // 06
        val year = format("yyyy", history?.date_created) as String // 2013

        val timeExit: String
        if (history?.date_exited != null){
            timeExit = format("HH:mm:ss", history?.date_exited) as String
        } else {
            timeExit = "null"
        }
        val timeEntry = format("HH:mm:ss", history?.date_created) as String

        if (history != null) {
            dateview.setText("$dayOfTheWeek, $monthString $day")
            yearview.setText(year)
            bldgview.setText(history.building_name)
            roomview.setText(history.room_name)
            timeEntryView.setText("Entry time: $timeEntry")
            timeExitView.setText("Exit time: $timeExit")
        }
    }

    override fun getItemCount(): Int {
        return mHistory!!.size
    }

    // Returns the total count of items in the list
//    override fun getItemCount(): Int {
//        return mHistory?.size
//    }
}