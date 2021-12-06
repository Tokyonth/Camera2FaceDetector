package com.tokyonth.facedetector.view

import android.graphics.Outline
import android.graphics.Rect
import android.view.View
import android.view.ViewOutlineProvider

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
