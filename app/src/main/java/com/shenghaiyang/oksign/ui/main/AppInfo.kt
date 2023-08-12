package com.shenghaiyang.oksign.ui.main

data class AppInfo(
    val appName: String,
    val packageName: String,
    val signatures: List<SignatureInfo>,
)

data class SignatureInfo(
    val md5: String,
    val sha1: String,
)
