package com.shenghaiyang.oksign.ui.main

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.twotone.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shenghaiyang.oksign.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    navigateToLibrary: () -> Unit,
    navigateToAbout: () -> Unit,
) {

    var appInfoState by remember {
        mutableStateOf<AppInfo?>(null)
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize(),
    ) {
        val query by viewModel.queryState.collectAsState()
        var actionMoreExpanded by remember {
            mutableStateOf(false)
        }
        SearchBar(
            query = query,
            onQueryChange = {
                viewModel.updateQuery(it)
            },
            onSearch = {
                viewModel.updateQuery(it)
            },
            active = false,
            onActiveChange = {

            },
            placeholder = {
                Text(text = stringResource(id = R.string.search_hint))
            },
            leadingIcon = {
                Box {
                    Image(
                        painter = painterResource(id = R.mipmap.ic_launcher),
                        contentDescription = "",
                        modifier = Modifier.size(36.dp),
                    )
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "",
                        modifier = Modifier
                            .size(18.dp)
                            .alpha(0.8f)
                            .background(MaterialTheme.colorScheme.background, CircleShape)
                            .padding(2.dp)
                            .align(Alignment.BottomEnd),
                    )
                }
            },
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 14.dp, vertical = 8.dp)
                .fillMaxWidth()
                .height(56.dp),
            trailingIcon = {
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
        ) {

        }

        val result by viewModel.appsResultState.collectAsState()
        LazyColumn(
            contentPadding = WindowInsets.navigationBars.asPaddingValues(),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            items(
                result,
                key = { appInfo -> appInfo.packageName },
                contentType = { "app_item" },
            ) { appInfo ->
                AppItem(
                    appInfo = appInfo,
                    showSignatures = {
                        appInfoState = appInfo
                    },
                )
            }
        }

    }

    with(appInfoState) {
        if (null != this) {
            val context = LocalContext.current
            Dialog(
                onDismissRequest = {
                    appInfoState = null
                },
            ) {
                LazyColumn {
                    itemsIndexed(
                        signatures
                    ) { index, signatureInfo ->
                        ListItem(
                            headlineContent = {
                                Text(text = "md5: ${signatureInfo.md5}")
                            },
                            trailingContent = {
                                Icon(Icons.TwoTone.Share, contentDescription = "")
                            },
                            modifier = Modifier.clickable {
                                val intent = Intent(Intent.ACTION_SEND)
                                intent.putExtra(Intent.EXTRA_TEXT, signatureInfo.md5)
                                intent.type = "text/plain"
                                context.startActivity(Intent.createChooser(intent, null))
                            }
                        )
                        ListItem(
                            headlineContent = {
                                Text(text = "sha1: ${signatureInfo.sha1}")
                            },
                            trailingContent = {
                                Icon(Icons.TwoTone.Share, contentDescription = "")
                            },
                            modifier = Modifier.clickable {
                                val intent = Intent(Intent.ACTION_SEND)
                                intent.putExtra(Intent.EXTRA_TEXT, signatureInfo.sha1)
                                intent.type = "text/plain"
                                context.startActivity(Intent.createChooser(intent, null))
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun AppItem(
    appInfo: AppInfo,
    showSignatures: () -> Unit,
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
        },
    )

}