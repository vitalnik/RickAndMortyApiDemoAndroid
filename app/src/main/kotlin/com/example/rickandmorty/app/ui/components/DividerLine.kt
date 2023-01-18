package com.example.rickandmorty.app.ui.components

import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun DividerLine() {
    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f))
}