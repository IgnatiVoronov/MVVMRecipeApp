package com.example.mvvmrecipeapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.mvvmrecipeapp.presentation.components.CircularIndeterminateProgressBar
import com.example.mvvmrecipeapp.presentation.components.DefaultSnackbar

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = LightBlack,
    surface = DarkGray
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun MVVMRecipeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    displayProgressBar: Boolean,
    snackbarHostState: SnackbarHostState,
    content: @Composable () -> Unit,

    ) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography
    )
    {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = if(!darkTheme) Color.Gray else Color.Black)
        ){
            content()
            CircularIndeterminateProgressBar(isDisplayed = displayProgressBar)
            DefaultSnackbar(
                snackbarHostState = snackbarHostState,
                onDismiss = { snackbarHostState.currentSnackbarData?.dismiss() },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}