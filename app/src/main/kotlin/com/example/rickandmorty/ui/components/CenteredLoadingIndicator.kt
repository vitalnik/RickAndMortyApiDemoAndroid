package com.example.rickandmorty.ui.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable

@Composable
fun CenteredLoadingIndicator() {
    Centered {
        CircularProgressIndicator()
    }
}