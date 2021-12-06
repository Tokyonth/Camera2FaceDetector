package com.tokyonth.facedetector

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tokyonth.facedetector.camera.CameraView
import com.tokyonth.facedetector.camera.ICamera
import com.tokyonth.facedetector.databinding.ActivityMainBinding
import com.tokyonth.facedetector.utils.FileUtils
import com.tokyonth.facedetector.utils.permission.PermissionUtil
import com.tokyonth.facedetector.widget.PhotoDialog
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var vb: ActivityMainBinding

    private var countImage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(vb.root)

        PermissionUtil(this).request(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) { granted, _ ->
            if (granted) {
                initCamera()
                initMediaFolder()
            }
        }
    }

    private val takePictureCallbacks = object : ICamera.CaptureImageCallbacks {

        override fun captureSucceeded(picture: Bitmap) {

            runOnUiThread {
                vb.countImageCapture.visibility = View.INVISIBLE
                PhotoDialog(this@MainActivity).apply {
                    setPhoto(picture)
                }.show()
            }

        }

        override fun captureBurstSucceeded(picture: Bitmap?, sessionBurstFinished: Boolean) {
            runOnUiThread {
                vb.countImageCapture.visibility = View.VISIBLE
                countImage++
                vb.countImageCapture.text = countImage.toString()
                if (sessionBurstFinished) {
                    Handler(Looper.myLooper()!!).postDelayed({
                        vb.countImageCapture.visibility = View.INVISIBLE
                    }, 300)
                    Toast.makeText(
                        applicationContext,
                        "Session capture burst completed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        override fun countDownTimerCaptureWithDelay(time: Long, ended: Boolean) {
            runOnUiThread {
                if (ended) {
                    vb.countDown.visibility = View.INVISIBLE
                } else {
                    vb.countDown.visibility = View.VISIBLE
                    vb.countDown.text = (time / 1000).toString()
                }
            }
        }

        override fun captureImageFailed(e: Exception) {
            Log.e(this.toString(), e.message ?: e.toString())
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initCamera() {
        val cameraView = CameraView()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, cameraView, CameraView::class.toString())
            //.addToBackStack(null)
            .commit()

        vb.switchCamera.setOnClickListener {
            cameraView.switchCamera()
        }

        /**************************** Capture ************************************************/

        vb.capture.setOnClickListener {
            cameraView.capture(takePictureCallbacks)

        }

        /****************************** Burst **********************************************/

        vb.burst.setOnLongClickListener {
            countImage = 0
            cameraView.captureBurst(takePictureCallbacks)

            true
        }

        vb.burst.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    cameraView.stopCaptureBurst()
                    Handler(Looper.myLooper()!!).postDelayed({
                        vb.countImageCapture.visibility = View.INVISIBLE
                    }, 300)
                }
            }

            false
        }

        /***************************** Burst free hand ************************************************/

        vb.burstFreeHand.setOnClickListener {
            countImage = 0
            cameraView.captureBurstFreeHand(takePictureCallbacks, 10, 100L, 5000L)
        }
    }

    private fun initMediaFolder() {
        val file = File(FileUtils.getPathMediaFolder())
        if (!file.exists()) {
            file.mkdirs()
        }
    }

}
