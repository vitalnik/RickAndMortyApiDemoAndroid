package com.example.rickandmorty.app.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R
import com.example.rickandmorty.app.ui.theme.shapes

@Composable
fun SearchPanel(
    searchValue: String,
    onSearchValueChange: (value: String) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(bottom = 8.dp)
    ) {

        OutlinedTextField(
            value = searchValue,
            shape = shapes.large,
            label = { Text(text = stringResource(id = R.string.search_by_name)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = stringResource(id = R.string.search_by_name),
                )
            },
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {
                onSearchValueChange(it)
            }
        )
    }
}
