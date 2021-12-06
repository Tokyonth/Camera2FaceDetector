package com.tokyonth.facedetector.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import com.tokyonth.facedetector.databinding.DialogPhotoBinding

class PhotoDialog(context: Context) : Dialog(context) {

    private var vb: DialogPhotoBinding = DialogPhotoBinding.inflate(LayoutInflater.from(context))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }

    fun setPhoto(bitmap: Bitmap) {
        vb.ivDialogPhoto.setImageBitmap(bitmap)
    }

}
