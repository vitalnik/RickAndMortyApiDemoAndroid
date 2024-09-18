package com.example.rickandmorty.app.ui.components

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun DividerLine() {
    HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f))
}
