package com.tokyonth.facedetector.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tokyonth.facedetector.databinding.FragmentCameraBinding
import com.tokyonth.facedetector.view.onSurfaceListener
import com.tokyonth.facedetector.view.setRoundOutlineProvider

/**
 * Wrapper camera using Fragment instead of ViewGroup, because i want handle state camera when
 * system calls methods of Lifecycle
 */
class CameraView : Fragment() {

    private lateinit var vb: FragmentCameraBinding
    private var iCamera: ICamera? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentCameraBinding.inflate(LayoutInflater.from(requireContext()))
        initView()
        return vb.root
    }

    override fun onStart() {
        super.onStart()
        if (vb.cameraPre.isAvailable) {
            iCamera?.openCamera(ICamera.CameraFace.BACK)
        } else {
            vb.cameraPre.onSurfaceListener(
                onSurfaceAvailable = {
                    iCamera?.openCamera(ICamera.CameraFace.BACK)
                }
            )
        }
    }

    override fun onStop() {
        super.onStop()
        stopCaptureBurstFreeHand()
        iCamera?.closeCamera()
    }

    private fun initView() {
        iCamera = ICamera.Builder(requireContext())
            .setTargetView(vb.cameraPre.apply {
                setRoundOutlineProvider()
            })
            .setFaceDetectionView(vb.faceBorder.apply {
                setRoundOutlineProvider()
            }).build()
    }

    fun switchCamera() {
        iCamera?.switchCamera()
    }

    fun capture(takePictureCallbacks: ICamera.CaptureImageCallbacks) {
        iCamera?.capture(takePictureCallbacks)
    }

    fun captureBurst(takePictureCallbacks: ICamera.CaptureImageCallbacks) {
        iCamera?.captureBurst(takePictureCallbacks)
    }

    fun stopCaptureBurst() {
        iCamera?.stopCaptureBurst()
    }

    fun captureBurstFreeHand(
        takePictureCallbacks: ICamera.CaptureImageCallbacks,
        amountImage: Int, distance: Long, delayMs: Long
    ) {
        iCamera?.captureBurstFreeHand(takePictureCallbacks, amountImage, distance, delayMs)
    }

    fun stopCaptureBurstFreeHand() {
        iCamera?.stopCaptureBurstFreeHand()
    }

}
