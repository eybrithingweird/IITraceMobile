package com.example.iitrace.network.data.models

import com.google.gson.annotations.SerializedName
import java.util.*

class HistoryResult {
    var id: Int = 0
    lateinit var date_entered: Date
    lateinit var date_exited: Date
    lateinit var date_created: Date
    lateinit var date_updated: Date
    var person: Int = 0
    var room: Int = 0
    lateinit var room_name: String
    lateinit var building_name: String
}