package dev.aurakai.auraframefx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aurakai.auraframefx.navigation.AppNavGraph
import dev.aurakai.auraframefx.ui.components.BottomNavigationBar
import dev.aurakai.auraframefx.ui.theme.AuraFrameFXTheme
import dev.aurakai.auraframefx.ui.theme.ThemeViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuraFrameFXTheme {
                val themeViewModel: ThemeViewModel = hiltViewModel()
                MainScreen(themeViewModel = themeViewModel)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Perform any cleanup here if needed
    }
}

// New: a preview-friendly content composable that accepts a lambda for theme commands
@Composable
internal fun MainScreenContent(
    processThemeCommand: (String) -> Unit,
    paddingValues: Any
) {
    val navController = rememberNavController()

    var showDigitalEffects by remember { mutableStateOf(true) }
    var command by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row {
                TextField(
                    value = command,
                    onValueChange = { command = it },
                    label = { Text("Enter theme command") },
                    state = TODO()
                )
                Button(onClick = { processThemeCommand(command) }) {
                    Text("Apply")
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .let { base ->
                        if (showDigitalEffects) {
                            base.digitalPixelEffect()
                        } else {
                            base
                        }
                    }
            ) {
                AppNavGraph(navController = navController)
            }
        }
    }
}

private fun Scaffold(bottomBar: () -> Unit, content: Any) {
    TODO("Not yet implemented")
}

fun Column(
    modifier: Any,
    content: () -> ComposableFunction1<BoxScope, Unit>
) {
    TODO("Not yet implemented")
}

fun Row(content: () -> ComposableFunction1<RowScope, Unit>) {}

fun Box(
    modifier: Modifier,
    content: () -> Unit
): ComposableFunction1<BoxScope, Unit> {
    TODO("Provide the return value")
}

fun Button(
    onClick: () -> Unit,
    content: () -> Unit
): ComposableFunction1<RowScope, Unit> {
    TODO("Provide the return value")
}

// Keep original API used by Activity: delegate to the content with the real ViewModel
@Composable
internal fun MainScreen(
    themeViewModel: ThemeViewModel
) {
    MainScreenContent(processThemeCommand = { themeViewModel.processThemeCommand(it) })
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AuraFrameFXTheme {
        // For preview, use a no-op lambda for the theme command handler
        MainScreenContent(processThemeCommand = { /* no-op in preview */ })
    }
}

// Extension function placeholder - this should be implemented elsewhere
fun Modifier.digitalPixelEffect(): Modifier = this
