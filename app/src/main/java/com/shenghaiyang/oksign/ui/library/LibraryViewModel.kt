package com.shenghaiyang.oksign.ui.library

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LibraryViewModel : ViewModel() {

    private val _librariesState = MutableStateFlow(getLibraries())
    val librariesState = _librariesState.asStateFlow()

}