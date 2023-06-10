package com.example.iitrace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.iitrace.databinding.ScanQrBinding
import com.example.iitrace.network.data.requests.ScanQRERequest
import com.example.iitrace.network.data.requests.ScanQRRequest
import com.example.iitrace.viewmodel.IITraceViewModel
import java.io.IOException
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.android.gms.vision.Detector.Detections
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ScanQRActivity : AppCompatActivity() {
    private val requestCodeCameraPermission = 1001
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private var scannedValue = ""
    private lateinit var binding: ScanQrBinding
    private var flagger: Boolean = false
    private val iitraceViewModel: IITraceViewModel by viewModels()

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finish()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun observeScanQR() {
        iitraceViewModel._scanQRState.observe(this) { data ->
            val loadingBar = findViewById<ProgressBar>(R.id.pbScanning)
            val fader = findViewById<View>(R.id.viewFaderScan)
            when {
                data.isLoading -> {
                    getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    loadingBar.visibility = View.VISIBLE
                    fader.visibility = View.VISIBLE
                }
                data.data != null -> {
                    loadingBar.visibility = View.INVISIBLE
                    fader.visibility = View.INVISIBLE

                    if (data.data!!.details != null) {
                        Toast.makeText(this@ScanQRActivity, "Processing successful!", Toast.LENGTH_LONG).show()
                        if (!data?.data?.message?.isEmpty()!!) {
                            val intent = Intent(this, QRHistoryActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        val msgSend = data.data!!.message
                        Toast.makeText(this@ScanQRActivity, msgSend, Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                else -> {
                    loadingBar.visibility = View.INVISIBLE
                    fader.visibility = View.INVISIBLE
                    Toast.makeText(this@ScanQRActivity, "Failure: ${data.error}", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun observeScanEQR() {
        iitraceViewModel._scanQREState.observe(this) { data ->
            val loadingBar = findViewById<ProgressBar>(R.id.pbScanning)
            val fader = findViewById<View>(R.id.viewFaderScan)
            when {
                data.isLoading -> {
                    getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    loadingBar.visibility = View.VISIBLE
                    fader.visibility = View.VISIBLE
                }
                data.data != null -> {
                    loadingBar.visibility = View.INVISIBLE
                    fader.visibility = View.INVISIBLE

                    Toast.makeText(this@ScanQRActivity, "Processing successful!", Toast.LENGTH_LONG).show()
                    if (!data?.data?.building_name?.isEmpty()!!) {
                        val intent = Intent(this, QRHistoryActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                else -> {
                    loadingBar.visibility = View.INVISIBLE
                    fader.visibility = View.INVISIBLE
                    Toast.makeText(this@ScanQRActivity, "Failure: ${data.error}", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding = ScanQrBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)

        if (timeOfDay < 18) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.red_orange)
        } else if (timeOfDay > 18 || timeOfDay == 18) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.blue_purple)
        }

        val pullToRefresh = findViewById<SwipeRefreshLayout>(R.id.viewRefresher)
        pullToRefresh.setOnRefreshListener {
            val intent = Intent(this, ScanQRActivity::class.java)
            startActivity(intent)
            finish()
        }

        val chevron = findViewById<ImageButton>(R.id.ibChevron)
        chevron.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        if (ContextCompat.checkSelfPermission(
                this@ScanQRActivity, android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askForCameraPermission()
        } else {
            setupControls()
        }

//        val aniSlide: Animation =
//            AnimationUtils.loadAnimation(this@ScanQRActivity, R.anim.scanner_animation)
//        binding.barcodeLine.startAnimation(aniSlide)
    }

    private fun setupControls() {
        barcodeDetector =
            BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build()

        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(1280, 720)
            .setAutoFocusEnabled(true) //you should add this feature
            .build()

        binding.cameraSurfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    //Start preview after 1s delay
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            @SuppressLint("MissingPermission")
            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                try {
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })


        val token = SessionManager.getToken(applicationContext)
        fun getHeaderMap(): Map<String, String> {
            return mapOf("Authorization" to "Token $token")
        }

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {

            override fun release() {
                Toast.makeText(applicationContext, "Scanner has been closed", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun receiveDetections(detections: Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() == 1 && !flagger) {
                    flagger = true
                    scannedValue = barcodes.valueAt(0).rawValue

                    //Don't forget to add this line printing value or finishing activity must run on main thread
                    runOnUiThread {
                        cameraSource.stop()
//                        Toast.makeText(this@ScanQRActivity, "value- $scannedValue", Toast.LENGTH_SHORT).show()
                        Log.d("QR", "$scannedValue")
                        val separated = scannedValue.split("-".toRegex())
                        if (separated.size == 1) {
                            Toast.makeText(this@ScanQRActivity, "Invalid QR code! Try again.", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@ScanQRActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            val room = separated[0]
                            val qrType = separated[1]

                            if (qrType == "entrance") {
                                val scanqrRequest = ScanQRRequest(room.toInt())
                                iitraceViewModel.scans(getHeaderMap(), scanqrRequest)
                                observeScanQR()
                            } else if (qrType == "exit") {
                                val scanEqrRequest = ScanQRERequest(room.toInt())
                                iitraceViewModel.exitscans(getHeaderMap(), scanEqrRequest)
                                observeScanEQR()
                            } else {
                                Toast.makeText(this@ScanQRActivity, "Invalid QR code! Try again.", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@ScanQRActivity, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                } else {
//                    Toast.makeText(this@ScanQRActivity, "value- else", Toast.LENGTH_SHORT).show()
                    Log.d("Toast", "value- else, $barcodes")
                }
            }
        })
    }

    private fun askForCameraPermission() {
        ActivityCompat.requestPermissions(
            this@ScanQRActivity,
            arrayOf(android.Manifest.permission.CAMERA),
            requestCodeCameraPermission
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodeCameraPermission && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupControls()
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraSource.stop()
    }
}