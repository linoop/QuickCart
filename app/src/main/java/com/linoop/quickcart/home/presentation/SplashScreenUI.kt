package com.linoop.quickcart.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.linoop.quickcart.R
import com.linoop.quickcart.navigation.Screen
import com.linoop.quickcart.ui.theme.Purple40
import kotlinx.coroutines.delay

@Composable
fun SplashScreenUI(
    navController: NavController = rememberNavController()
) {
    LaunchedEffect(Unit) {
        delay(2000)
        gotoHome(navController)
    }
    DrawSplashScreen()
}

fun gotoHome(navController: NavController) {
    navController.navigate(Screen.HomeScreen.route) {
        popUpTo(Screen.SplashScreen.route) { inclusive = true }
    }
}

@Composable
private fun DrawSplashScreen() {
    Scaffold {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                color = Purple40,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    SplashScreenUI()
}