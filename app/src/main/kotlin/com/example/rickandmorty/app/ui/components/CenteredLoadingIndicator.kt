package com.example.rickandmorty.app.ui.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable

@Composable
fun CenteredLoadingIndicator() {
    Centered {
        CircularProgressIndicator()
    }
}
