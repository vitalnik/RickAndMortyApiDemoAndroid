package com.example.rickandmorty.app.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.rickandmorty.R

@Composable
fun RetryDialog(
    errorMessage: String,
    onRetryClick: () -> Unit,
    onDismissClick: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.oops))
        },
        text = {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.titleMedium
            )
        },
        onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = {
                onRetryClick()
            }) {
                Text(text = stringResource(id = R.string.retry))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismissClick()
            }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    )
}