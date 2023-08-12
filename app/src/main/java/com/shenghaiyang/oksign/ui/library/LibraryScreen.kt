package com.shenghaiyang.oksign.ui.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shenghaiyang.oksign.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel = viewModel(),
    navigatePop: () -> Unit,
    navigateToUrl: (url: String) -> Unit,
) {
    val libraries by viewModel.librariesState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.library))
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigatePop,
                        content = {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "",
                            )
                        },
                    )
                },
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(8.dp),
        ) {
            items(libraries) { library ->
                LibraryItem(
                    library = library,
                    onClick = {
                        navigateToUrl(library.url)
                    },
                )
            }
        }
    }
}

@Composable
fun LibraryItem(
    library: Library,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        val scrollState = rememberScrollableState { it }
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable {
                    onClick()
                },
        ) {
            Text(
                text = library.name,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                ),
                modifier = Modifier.padding(0.dp),
            )

            Text(
                text = library.licenseName,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                ),
                modifier = Modifier.padding(top = 1.dp),
            )
            Text(
                text = library.licenseContent,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                ),
                modifier = Modifier.scrollable(
                    state = scrollState,
                    orientation = Orientation.Horizontal,
                ),
            )
        }
    }
}
