package com.shenghaiyang.oksign.ui.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shenghaiyang.oksign.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    navigateToLibrary: () -> Unit,
    navigateToAbout: () -> Unit,
) {

    val query by viewModel.queryState.collectAsState()
    val result by viewModel.appsResultState.collectAsState()

    var actionMoreExpanded by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.app_name))
                },
                actions = {
                    IconButton(
                        onClick = { actionMoreExpanded = !actionMoreExpanded },
                    ) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
                    }
                    DropdownMenu(
                        expanded = actionMoreExpanded,
                        onDismissRequest = { actionMoreExpanded = false },
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                actionMoreExpanded = false
                                navigateToLibrary()
                            },
                            text = {
                                Text(text = stringResource(R.string.library))
                            },
                        )
                        DropdownMenuItem(
                            onClick = {
                                actionMoreExpanded = false
                                navigateToAbout()
                            },
                            text = {
                                Text(text = stringResource(R.string.about))
                            },
                        )
                    }
                },
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            stickyHeader(
                key = "query",
                contentType = "query",
            ) {
                TextField(
                    value = query,
                    onValueChange = { query ->
                        viewModel.updateQuery(query)
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.search_hint))
                    },
                    leadingIcon = {
                        Icon(
                            Icons.TwoTone.Search,
                            contentDescription = "",
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search,
                    ),
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                )
            }
            items(
                result,
                key = { appInfo -> appInfo.packageName },
                contentType = { "appInfo" },
            ) { appInfo ->
                AppItem(
                    appInfo = appInfo,
                    showSignatures = {
                    },
                )
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppItem(
    appInfo: AppInfo,
    showSignatures: () -> Unit,
) {
    var expand by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .padding(8.dp),
    ) {
        ListItem(
            headlineContent = {
                Text(text = appInfo.appName)
            },
            supportingContent = {
                Text(text = appInfo.packageName)
            },
            modifier = Modifier.clickable {
                showSignatures()

                expand = true
            },
        )
        DropdownMenu(
            expanded = expand,
            onDismissRequest = {
                expand = false
            },
            modifier = Modifier.align(Alignment.End),
        ) {
            appInfo.signatures.forEachIndexed { index, signatureInfo ->
                DropdownMenuItem(
                    onClick = {
                        expand = false
                    },
                    text = {
                        Text(text = "signature-$index md5: ${signatureInfo.md5}")
                    },
                )
                DropdownMenuItem(
                    onClick = {
                        expand = false
                    },
                    text = {
                        Text(text = "signature-$index sha1: ${signatureInfo.sha1}")
                    },
                )
            }
        }
    }
}