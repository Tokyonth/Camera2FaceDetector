package com.tokyonth.facedetector.view

import android.graphics.Outline
import android.graphics.Rect
import android.graphics.SurfaceTexture
import android.view.TextureView
import android.view.View
import android.view.ViewOutlineProvider

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

fun View.setRoundOutlineProvider() {

    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            val rect = if (measuredWidth <= measuredHeight) {
                Rect(
                    0,
                    (measuredHeight - measuredWidth) / 2,
                    measuredWidth,
                    (measuredHeight + measuredWidth) / 2
                )
            } else {
                Rect(
                    (measuredWidth - measuredHeight) / 2,
                    0,
                    measuredHeight,
                    (measuredHeight + measuredWidth) / 2
                )
            }
            outline?.setOval(rect)
        }
    }
    clipToOutline = true

}
