package com.shenghaiyang.oksign.library

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Library(
        @SerialName("name")
        val name: String,
        @SerialName("url")
        val url: String,
        @SerialName("licenseName")
        val licenseName: String,
        @SerialName("licenseContent")
        val licenseContent: String
)
