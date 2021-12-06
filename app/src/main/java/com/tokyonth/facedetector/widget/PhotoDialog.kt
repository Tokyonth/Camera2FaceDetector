package com.tokyonth.facedetector.widget

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import com.tokyonth.facedetector.databinding.DialogPhotoBinding

class PhotoDialog(context: Context) : AlertDialog.Builder(context) {

    private var vb: DialogPhotoBinding = DialogPhotoBinding.inflate(LayoutInflater.from(context))

    init {
        setTitle("照片预览")
        setPositiveButton("确定", null)
        setView(vb.root)
    }

    fun setPhoto(bitmap: Bitmap) {
        vb.ivDialogPhoto.setImageBitmap(bitmap)
    }

}
