package com.tokyonth.facedetector.utils.permission

data class Permission constructor(
    val permission: String,
    val granted: Boolean,
    val preventAskAgain: Boolean
)
