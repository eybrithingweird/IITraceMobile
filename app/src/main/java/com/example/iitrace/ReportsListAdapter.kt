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
import com.example.iitrace.network.data.responses.ReportsResponse

class ReportsListAdapter (
    private val mReports: ArrayList<ReportsResponse>
) : RecyclerView.Adapter<ReportsListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textListDate = itemView.findViewById<TextView>(R.id.tvListDate)
        val textListDisease = itemView.findViewById<TextView>(R.id.tvListDisease)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val reportsView = inflater.inflate(R.layout.row_reports, parent, false)
        return ViewHolder(reportsView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ReportsListAdapter.ViewHolder, position: Int) {
        val reports: ReportsResponse = mReports.get(position)
        val dateview = viewHolder.textListDate
        val diseaseview = viewHolder.textListDisease

//        val dayOfTheWeek: String
//        val monthNumber: String

//        dayOfTheWeek = format("EEE", reports.date_created) as String // Thursday
        val day: String = format("dd", reports.date_created) as String // 25
        val monthString: String = format("MMM", reports.date_created) as String // May
//        monthNumber = format("MM", reports.date_created) as String // 05
        val year: String = format("yyyy", reports.date_created) as String // 2023

        dateview.text = "$monthString $day, $year"
        diseaseview.text = reports.disease_name
    }

    override fun getItemCount(): Int {
        return mReports.size
    }

}