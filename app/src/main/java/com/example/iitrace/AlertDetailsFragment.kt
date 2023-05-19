package com.example.iitrace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class AlertDetailsFragment : Fragment() {

    companion object {
        fun newInstance(): AlertDetailsFragment {
            return AlertDetailsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alert_details, container, false)
    }

}