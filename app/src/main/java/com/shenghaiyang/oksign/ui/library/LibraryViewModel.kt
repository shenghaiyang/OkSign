package com.shenghaiyang.oksign.ui.library

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shenghaiyang.oksign.R
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.buffer
import okio.source

@OptIn(ExperimentalStdlibApi::class)
class LibraryViewModel(
    private val application: Application,
) : AndroidViewModel(application) {

    private val _librariesState = MutableStateFlow<List<Library>>(emptyList())
    val librariesState = _librariesState.asStateFlow()

    init {
        viewModelScope.launch {
            val result = application.resources.runCatching {
                openRawResource(R.raw.libraries).source().buffer().use {
                    val moshi = Moshi.Builder()
                        .build()
                    val adapter = moshi.adapter<List<Library>>()
                    adapter.fromJson(it).orEmpty()
                }
            }
            val list = result.getOrNull().orEmpty()
            _librariesState.value = list
        }
    }
}