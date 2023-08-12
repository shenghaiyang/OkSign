package com.shenghaiyang.oksign.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shenghaiyang.oksign.Routes
import com.shenghaiyang.oksign.ui.about.AboutScreen
import com.shenghaiyang.oksign.ui.library.LibraryScreen
import com.shenghaiyang.oksign.ui.main.MainScreen

@Composable
fun App() {
    val navController = rememberNavController()

    val navigatePop: () -> Unit = {
        navController.popBackStack()
    }
    val uriHandler = LocalUriHandler.current
    val navigateToUrl: (String) -> Unit = { url ->
        uriHandler.openUri(url)
    }
    NavHost(
        navController = navController,
        startDestination = Routes.Main,
    ) {
        composable(Routes.Main) {
            MainScreen(
                navigateToLibrary = {
                    navController.navigate(Routes.Library)
                },
                navigateToAbout = {
                    navController.navigate(Routes.About)
                },
            )
        }
        composable(Routes.Library) {
            LibraryScreen(
                navigatePop = navigatePop,
                navigateToUrl = navigateToUrl,
            )
        }
        composable(Routes.About) {
            AboutScreen(
                navigatePop = navigatePop,
                navigateToUrl = navigateToUrl,
            )
        }
    }

}