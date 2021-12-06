package com.tokyonth.facedetector.view

import android.graphics.SurfaceTexture
import android.view.TextureView

internal fun TextureView.onSurfaceListener(onSurfaceAvailable: (SurfaceTexture) -> Unit) {

    if (isAvailable) {
        this@onSurfaceListener.surfaceTexture?.let { onSurfaceAvailable(it) }
    }

    surfaceTextureListener = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
            onSurfaceAvailable(surface)
        }

        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {

        }

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
            return false
        }

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {

        }

    }

}
