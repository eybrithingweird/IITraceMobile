package com.example.iitrace

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi

class PopUpClass {
    //PopupWindow display method
//    @RequiresApi(Build.VERSION_CODES.M)
//    @SuppressLint("MissingInflatedId")
    fun showPopupWindow(view: View, titleSetup: String, textSetup: String) {
        //Create a View object yourself through inflater
        val inflater =
            view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.window_popup, null)

        val dimmer = popupView.findViewById<FrameLayout>(R.id.viewDimmer)
        dimmer.background.alpha = 220

        //Specify the length and width through constants
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT

        //Make Inactive Items Outside Of PopupWindow
        val focusable = true

        //Create a window with our parameters
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        //Initialize the elements of our window, install the handler
        val test = popupView.findViewById<TextView>(R.id.titleText)
        val test2 = popupView.findViewById<TextView>(R.id.messageText)
        test.text = titleSetup
        test2.text = textSetup

        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener { v, event -> //Close the window when clicked
            popupWindow.dismiss()
            dimmer.background.alpha = 0
            true
        }
    }
}