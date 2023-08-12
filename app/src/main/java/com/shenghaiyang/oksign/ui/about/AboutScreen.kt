package com.shenghaiyang.oksign.ui.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shenghaiyang.oksign.R

private const val GITHUB_URL = "https://github.com/shenghaiyang/OkSign"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    navigatePop: () -> Unit,
    navigateToUrl: (url: String) -> Unit,
) {
    val appName = stringResource(R.string.app_name)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.about))
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigatePop,
                        content = {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = appName,
                            )
                        },
                    )
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher),
                contentDescription = "",
                modifier = Modifier.size(72.dp)
            )
            Text(
                text = appName,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                ),
                modifier = Modifier.padding(top = 12.dp),
            )
            Text(
                text = "",// TODO appVersion
            )
            Text(
                text = GITHUB_URL,
                modifier = Modifier
                    .padding(top = 32.dp)
                    .clickable {
                        navigateToUrl(GITHUB_URL)
                    },
            )
        }
    }
}