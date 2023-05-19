package com.example.iitrace

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.iitrace.viewmodel.IITraceViewModel

class QRListFragment : Fragment() {
    private val iitraceViewModel: IITraceViewModel by viewModels()

    companion object {
        fun newInstance(): QRListFragment {
            return QRListFragment()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)


    }

    private fun observeQRList() {
        iitraceViewModel._loginState.observe(this) { data ->
            val loadingBar =
            when {
                data.isLoading -> {

                }
                data.data != null -> {

                }
                else -> {

                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_qrhistory_details, container,
            false)
        val activity = activity as Context
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        recyclerView.adapter = DogListAdapter(activity)
        return view
    }
}