package com.shenghaiyang.oksign.ui.library

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Library(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "licenseName")
    val licenseName: String,
    @Json(name = "licenseContent")
    val licenseContent: String,
)
