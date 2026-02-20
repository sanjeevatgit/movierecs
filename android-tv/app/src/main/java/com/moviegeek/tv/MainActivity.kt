package com.moviegeek.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moviegeek.tv.ui.HomeScreen
import com.moviegeek.tv.ui.HomeViewModel
import com.moviegeek.tv.ui.theme.MovieGeekTvTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MovieGeekTvTheme {
                val vm: HomeViewModel = viewModel()
                val uiState by vm.uiState.collectAsState()
                HomeScreen(uiState = uiState, onRetry = vm::refresh)
            }
        }
    }
}
