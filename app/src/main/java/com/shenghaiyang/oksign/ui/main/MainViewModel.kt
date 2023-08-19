package com.shenghaiyang.oksign.ui.main

import android.app.Application
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okio.ByteString.Companion.toByteString

class MainViewModel constructor(
    private val application: Application,
) : AndroidViewModel(application) {

    private val _appsState = MutableStateFlow<List<AppInfo>>(emptyList())

    private val _queryState = MutableStateFlow("")
    val queryState = _queryState.asStateFlow()

    val appsResultState: StateFlow<List<AppInfo>> = combine(
        _appsState,
        _queryState,
    ) { apps, query ->
        if (query.isBlank()) {
            apps
        } else {
            apps.filter {
                it.packageName.contains(query) || it.appName.contains(query)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val packageManager = application.packageManager
            val apps = packageManager.getInstalledPackages(PackageManager.GET_SIGNATURES)
                .map { packageInfo ->
                    val signatures = packageInfo.signatures.map { signature ->
                        val bs = signature.toByteArray().toByteString()
                        SignatureInfo(
                            md5 = bs.md5().hex(),
                            sha1 = bs.sha1().hex(),
                        )
                    }.toList()
                    AppInfo(
                        appName = packageInfo.applicationInfo.loadLabel(packageManager).toString(),
                        packageName = packageInfo.packageName,
                        signatures = signatures,
                    )
                }
            _appsState.value = apps
        }
    }

    fun updateQuery(query: String) {
        viewModelScope.launch {
            _queryState.value = query
        }
    }

}